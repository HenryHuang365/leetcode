import re

def getRightVersion(user_version, user_type):
    latest_version = None
    latest_version_key = None

    for key in user_version.keys():
        key_parts = key.split('_')
        
        if key_parts[0] == user_type:
            version_number = int(key_parts[1]) if len(key_parts) > 1 else -1
            if latest_version is None or version_number > latest_version:
                latest_version = version_number
                latest_version_key = key

    return latest_version_key


def getRightVersionWithRegex(user_version, user_type):
    highest_version = -1
    highest_version_key = None
    
    # Compile a regex pattern that matches the user_type followed by an optional underscore and version number
    pattern = re.compile(f"^{user_type}(_(\\d+))?$")

    for key in user_version.keys():
        match = pattern.match(key)
        if match:
            version = int(match.group(2)) if match.group(2) else 0
            if version > highest_version:
                highest_version = version
                highest_version_key = key

    return highest_version_key

# key format: user type number is before the underline and version number is the after the underline. e.g "5_1", 5 is the user type and 1 is the version number.
user_version = {
    "1": {},
    "2": {},
    "3": {},
    "4": {},
    "5": {},
    "5_1": {},
    "5_2": {},
    "5_3": {},
    "5_4": {},
    "5_23": {},
    "15_1": {},
    "51_1": {},
    "51_2": {},
    "100": {},
    "#": {},
}

user_type = "5"

# Call the function with the given inputs
right_version_key = getRightVersion(user_version, user_type)
right_version_key_regex = getRightVersionWithRegex(user_version, user_type)
print(right_version_key)
print(right_version_key_regex)
