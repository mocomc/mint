#!/bin/sh
# ------------------
# run environment shell
# ------------------
APPTYPE=$1
IFID=$2
if [ "$APPTYPE" = "JAVA" ]
then
    java  jcall "$IFID" 1
fi

if [ "$APPTYPE" = "API" ]
then
    apiTest64 "$IFID" 1
fi

if [ "$APPTYPE" = "TMAX" ]
then
    tcall64 "$IFID" 1
fi
