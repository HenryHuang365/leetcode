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

interface AuthContextType {
  signout: () => void;
  getAuthUser: () => Promise<AuthUser>;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export function AuthGuard({ children }: PropsWithChildren): ReactNode {
  const [keycloakManager] = useState<KCManager>(new KCManager());

  const authToken = useAppSelector(selectToken);

  const handleSignout = async () => {
    await keycloakManager.logout();
  };

  const contextValue: AuthContextType = {
    signout: handleSignout,
    getAuthUser: keycloakManager.fetchUser,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {authToken !== null ? children : <Loading />}
    </AuthContext.Provider>
  );
  return children;
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
  private sessionDuration = 120; // 5 minutes in seconds

  constructor() {
    if (KCManager.instance) {
      return KCManager.instance;
    }

    KCManager.instance = this;
    this.sessionStartTime = Date.now() / 1000;

    this.keycloak
      .init({
        onLoad: "check-sso",
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
    const sleepPeriod = 1000 * 30;
    try {
      const tokenExpiryTime = KCManager.instance.keycloak.tokenParsed?.exp;
      const currentTime = Date.now() / 1000;
      const sessionExpiryTime = this.sessionStartTime + this.sessionDuration;
      console.log("This is the current time: ", currentTime);
      console.log("This is the sessionExpiryTime: ", sessionExpiryTime);

      if (currentTime >= sessionExpiryTime) {
        console.log('Session duration time expired, redirecting to login...');
        this.logoutAndRedirect(); // Redirect to login
      }

      if (tokenExpiryTime && tokenExpiryTime - sleepPeriod < currentTime) {
        console.log('Attempting to refresh token...');
        await KCManager.instance.keycloak.updateToken(-1);
        console.log('Token refreshed successfully');
        KCManager.instance.dispatch(
          setToken(KCManager.instance.keycloak.token ?? null)
        );
        KCManager.instance.dispatch(
          setRefreshToken(KCManager.instance.keycloak.refreshToken ?? null)
        );
        await this.fetchUser();        
      } else {
        console.log(`Token not yet expired: Expiry time: ${tokenExpiryTime}, Current time: ${currentTime}`);
      }
    
    } catch (error) {
      console.error('Error while refreshing token', error);
    }
    await new Promise((resolve) => setTimeout(resolve, sleepPeriod));
    KCManager.instance.keepFresh();
  }

  public logoutAndRedirect() {
    this.keycloak.logout();
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
      setToken(KCManager.instance.keycloak.token ?? null)
    );
    KCManager.instance.dispatch(
      setRefreshToken(KCManager.instance.keycloak.refreshToken ?? null)
    );
    KCManager.instance.fetchUser();
  }

  public async logout() {
    await KCManager.instance.keycloak.logout();
    KCManager.instance.dispatch(setToken(null));
    KCManager.instance.dispatch(setRefreshToken(null));
    KCManager.instance.dispatch(setAuthUser(null));
  }
}
