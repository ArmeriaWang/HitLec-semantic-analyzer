generate=0
build=0
while getopts "b:g:" arg; do
    case $arg in
    g)
        generate=$OPTARG
        ;;
    b)
        build=$OPTARG
        ;;
    ?)
        echo_usage
        exit
        ;;
    esac
done
if [ "$generate" -eq 1 ]; then
    java.exe -cp Lab1-1183710106-1.0-SNAPSHOT.jar main.LexicalParser < "test.c" > "test.lx"
    java.exe -cp Lab2-1183710106-1.0-SNAPSHOT.jar wang.armeria.whkcc.Whkcc < "test.lx" > "test.xml"
fi
if [ "$build" -eq 1 ]; then
    mvn install
    cp target/Lab3-1183710106-1.0-SNAPSHOT.jar ./
fi
java.exe -cp Lab3-1183710106-1.0-SNAPSHOT.jar wang.armeria.whkas.Whkas < "test.xml" > "test.ir"
