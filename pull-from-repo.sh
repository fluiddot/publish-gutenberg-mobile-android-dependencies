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

if [[ -z ${PACKAGE_NAME:-} ]]; then
    echo "A package name must be provided (e.g. 'react-native-video')."
    exit 1
fi
if [[ -z ${GITHUB_USER:-} ]]; then
    echo "A GitHub user must be provided (e.g. 'wordpress-mobile')."
    exit 1
fi
if [[ -z ${GIT_REF:-} ]]; then
    echo "A Git reference must be provided (i.e. branch name, tag or commit SHA)."
    exit 1
fi

read -r -p "Are you sure that you want to fetch changes from reference '${GIT_REF:-}' of '${GITHUB_USER:-}/${PACKAGE_NAME:-}' subtree? [y/N] " PROMPT_RESPONSE
if [[ $PROMPT_RESPONSE != "y" ]]; then
    exit 1
fi

if [[ ! -d "$PACKAGE_NAME" ]]; then
    echo "Adding '$PACKAGE_NAME' subtree"
    git subtree add --prefix "$PACKAGE_NAME" "git@github.com:$GITHUB_USER/$PACKAGE_NAME.git" $GIT_REF --squash
else
    echo "Pulling '$PACKAGE_NAME' subtree"
    git subtree pull --prefix "$PACKAGE_NAME" "git@github.com:$GITHUB_USER/$PACKAGE_NAME.git" $GIT_REF --squash
fi