#!/bin/sh
# ------------------
# run environment shell
# ------------------
echo 'ABABCM'
if[$1 -eq 'JAVA'] then
    java -cp /home/iip/nhiip/agent/linux004/iipagent/bin  jcall64 $2 1
fi

if[$1 -eq 'API'] then
    java -cp /home/iip/nhiip/agent/linux004/iipagent/bin  apiTest64 $2 1
fi

if[$1 -eq 'TMAX'] then
    java -cp /home/iip/nhiip/agent/linux004/iipagent/bin  tcall64 $2 1
fi
# ------------------
