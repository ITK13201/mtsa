REMOTE_JVM_FLAG	= -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
MEMORY_FLAG		= -Xmx20G
JAR_FLAG		= -jar
TARGET			= ./target/mtsa.jar
MTSA_TARGET     = TraditionalController
MTSA_RESULT_MODE = enabled

clean:
	mvn clean
.PHONY: build
build:
	mvn clean \
	&& mvn install -DskipTests=true
debug:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) -m $(MTSA_RESULT_MODE)
run:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) -m $(MTSA_RESULT_MODE)
debug-compile:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compile -f "../../testdata/AT（2, 2）.lts" -t $(MTSA_TARGET) -s 5000 -m $(MTSA_RESULT_MODE)
run-compile:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compile -f "../../testdata/AT（2, 2）.lts" -t $(MTSA_TARGET) -m $(MTSA_RESULT_MODE)
debug-compose:
	java $(REMOTE_JVM_FLAG) $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compose -f "../../testdata/AT（2, 2）.lts" -t $(MTSA_TARGET) -s 5000 -m $(MTSA_RESULT_MODE)
run-compose:
	java $(MEMORY_FLAG) $(JAR_FLAG) $(TARGET) compose -f "../../testdata/AT（2, 2）.lts" -t $(MTSA_TARGET) -m $(MTSA_RESULT_MODE)
