generate=0
build=0
while getopts "bg" arg; do
    case $arg in
    g)
        generate=1
        ;;
    b)
        build=1
        ;;
    ?)
        echo_usage
        exit
        ;;
    esac
done
if [ "$generate" -eq 1 ]; then
    java -cp Lab1-1183710106-1.0-SNAPSHOT.jar main.LexicalParser < "test.c" > "test.lx"
    java -cp Lab2-1183710106-1.0-SNAPSHOT.jar wang.armeria.whkcc.Whkcc < "test.lx" > "test.xml"
fi
if [ "$build" -eq 1 ]; then
    mvn install
    cp target/Lab3-1183710106-1.0-SNAPSHOT.jar ./
fi
java -cp Lab3-1183710106-1.0-SNAPSHOT.jar wang.armeria.whkas.Whkas < "test.xml" > "test.ir"
