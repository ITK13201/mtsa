REMOTE_JVM_FLAG	= -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
MEMORY_FLAG		= -Xmx20G
JAR_FLAG		= -jar
TARGET			= ./target/mtsa.jar

clean:
	mvn clean
.PHONY: build
build:
	mvn clean \
	&& mvn install -DskipTests=true
debug:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET)
run:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET)
debug-compile:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compile -f "../../testdata/AT（2, 2）.lts" -t TraditionalController -s 5000
run-compile:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compile -f "../../testdata/AT（2, 2）.lts" -t TraditionalController
debug-compose:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compose -f "../../testdata/AT（2, 2）.lts" -t TraditionalController -s 5000
run-compose:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compose -f "../../testdata/AT（2, 2）.lts" -t TraditionalController
