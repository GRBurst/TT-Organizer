#!/bin/bash -e
PACK=com.grburst.ttorganizer
ACT=.TTROrganizer
APP=/tmp/outputs/apk/ttorganizer
BUILD=packageDebug
if (( $# ==  1 )); then
    if [[ "$1" == "d" ]]; then
        BUILD="packageDebug -a";
        APP="$APP-debug-unaligned.apk"
    elif [[ "$1" == "r" ]]; then
        BUILD="assembleRelease";
        APP="$APP-release.apk"
    else
        BUILD = $1
        APP="$APP$2"
    fi
else
    BUILD="$@";
    APP="$APP-debug-unaligned.apk"
fi


echo "Building project $PACK with $BUILD"
./gradlew $BUILD

if [[ "$BUILD" == "packageDebug -a" ]]; then
    echo "Installing $APP"
    adb install -r $APP

    echo "Starting Activity"
    adb shell am start -n $PACK/$ACT
fi
