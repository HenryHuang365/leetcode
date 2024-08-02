"use client";

import { AuthRole, AuthUser } from "@/lib/auth/auth";
import {
  selectToken,
  setToken,
  setAuthUser,
  setRefreshToken,
  selectRefreshToken,
} from "@/lib/auth/authSlice";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import { PropsWithChildren, ReactNode, createContext, useState } from "react";
import KeyCloak from "keycloak-js";
import { Loaders } from "@/components/ui/loaders";
import { setKeycloakInitialised } from "@/lib/auth/keycloakSlice";
import { apiSlice } from "@/lib/api/apiSlice";
import { User } from "@/lib/user/user";
import { useEffect } from "react";

interface AuthContextType {
  signout: () => void;
  getAuthUser: () => Promise<AuthUser>;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

function isTokenValid(token: string | null): boolean {
  if (!token) return false;
  const tokenPayload = JSON.parse(atob(token.split(".")[1]));
  const expiryTime = tokenPayload.exp;
  const currentTime = Math.floor(Date.now() / 1000);
  return expiryTime > currentTime;
}

export function AuthGuard({ children }: PropsWithChildren): ReactNode {
  const [keycloakManager] = useState<KCManager>(new KCManager());

  const authToken = useAppSelector(selectToken);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  useEffect(() => {
    const tokenIsValid = isTokenValid(authToken);
    if (!tokenIsValid) {
      keycloakManager.logoutAndRedirect;
    }
    setIsAuthenticated(tokenIsValid);
  }, [authToken, keycloakManager]);

  const handleSignout = async () => {
    await keycloakManager.logout();
  };

  const contextValue: AuthContextType = {
    signout: handleSignout,
    getAuthUser: keycloakManager.fetchUser,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {isAuthenticated ? children : <Loading />}
    </AuthContext.Provider>
  );
}

function Loading() {
  return (
    <div className="flex flex-col h-screen justify-center items-center">
      <Loaders.indicator size="medium" layout="row" />
    </div>
  );
}

export class KCManager {
  private authToken = useAppSelector(selectToken);
  private refreshToken = useAppSelector(selectRefreshToken);
  private dispatch = useAppDispatch();
  public static instance: KCManager;

  private keycloak = new KeyCloak({
    url: process.env.NEXT_PUBLIC_KC_URI ?? "",
    realm: process.env.NEXT_PUBLIC_KC_REALM ?? "",
    clientId: process.env.NEXT_PUBLIC_KC_CLIENT_ID ?? "",
  });

  private sessionStartTime = 0; // Add this line
  private sessionDuration = 432000; // 5 days in seconds

  constructor() {
    if (KCManager.instance) {
      return KCManager.instance;
    }

    KCManager.instance = this;
    this.sessionStartTime = Date.now() / 1000;

    this.keycloak
      .init({
        onLoad: "login-required",
        silentCheckSsoRedirectUri: `${location.origin}/silent-check-sso.html`,
        token: this.authToken ?? undefined,
        refreshToken: this.refreshToken ?? undefined,
      })
      .then(() => {
        if (!this.keycloak.authenticated) {
          this.login();
        }
        this.dispatch(setKeycloakInitialised(true));
        this.keepFresh();
      });
  }

  private async keepFresh() {
    const sleepPeriod = 1000 * 55;
    try {
      console.log("Attempting to refresh token...");
      await KCManager.instance.keycloak.updateToken(70);
      console.log("Token refreshed successfully");
      await KCManager.instance.dispatch(
        setToken(KCManager.instance.keycloak.token ?? null)
      );
      await KCManager.instance.dispatch(
        setRefreshToken(KCManager.instance.keycloak.refreshToken ?? null)
      );
      await this.fetchUser();
    } catch (refreshError) {
      console.error("Token refresh failed", refreshError);
      this.logoutAndRedirect();
    }
    await new Promise((resolve) => setTimeout(resolve, sleepPeriod));
    KCManager.instance.keepFresh();
  }

  public logoutAndRedirect() {
    this.dispatch(setToken(null));
    this.dispatch(setRefreshToken(null));
    this.dispatch(setAuthUser(null));
    this.keycloak.login();
  }

  public async fetchUser(): Promise<AuthUser> {
    const userInfo: User = await KCManager.instance
      .dispatch(apiSlice.endpoints.getOwnUserDetails.initiate())
      .unwrap();
    const authUserInfo: AuthUser = {
      ...userInfo,
      authRole: KCManager.instance.keycloak.tokenParsed?.roles.includes(
        "iv-admin"
      )
        ? AuthRole.Admin
        : AuthRole.User,
    };
    KCManager.instance.dispatch(setAuthUser(authUserInfo));
    return authUserInfo;
  }

  public async login() {
    await KCManager.instance.keycloak.login();
    KCManager.instance.dispatch(
      await setToken(KCManager.instance.keycloak.token ?? null)
    );
    KCManager.instance.dispatch(
      await setRefreshToken(KCManager.instance.keycloak.refreshToken ?? null)
    );
    await KCManager.instance.fetchUser();
  }

  public async logout() {
    await KCManager.instance.keycloak.logout();
    KCManager.instance.dispatch(setToken(null));
    KCManager.instance.dispatch(setRefreshToken(null));
    KCManager.instance.dispatch(setAuthUser(null));
  }
}
