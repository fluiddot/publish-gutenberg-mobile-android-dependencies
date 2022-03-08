#!/bin/bash
set -euo pipefail

# Get arguments
while test $# -gt 0; do
  case "$1" in
    -h|--help)
      echo "options:"
      echo "-h, --help                              show brief help"
      echo "-p, --package                           package name (e.g. 'react-native-video')"
      echo "-u, --user                              GitHub user of the repository (e.g. 'wordpress-mobile')"
      echo "-r, --ref                               Git reference (i.e. branch name, tag or commit SHA)"
      exit 0
      ;;
    -p|--package*)
      shift
      PACKAGE_NAME=$1
      shift
      ;;
    -u|--user*)
      shift
      GITHUB_USER=$1
      shift
      ;;
    -r|--ref*)
      shift
      GIT_REF=$1
      shift
      ;;
    *)
      break
      ;;
  esac
done

if [[ ! -d "$PACKAGE_NAME" ]]; then
    echo "'$PACKAGE_NAME' subtree doesn't exist!"
    exit 1
else
    read -r -p "Are you sure that you want to push changes to reference '$GIT_REF' in '$GITHUB_USER/$PACKAGE_NAME' subtree? [y/N] " PROMPT_RESPONSE
    if [[ $PROMPT_RESPONSE != "y" ]]; then
        exit 1
    fi

    echo "Pushing changes to '$PACKAGE_NAME' subtree"
    git subtree push --prefix "$PACKAGE_NAME" "git@github.com:$GITHUB_USER/$PACKAGE_NAME.git" $GIT_REF
fi