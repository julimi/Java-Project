JFLAGS = -g
JC = javac
JVM= java 
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	*.java

MAIN = Snake 

default: classes

classes: $(CLASSES:.java=.class)

run:
	$(JVM) $(MAIN) ${ARGS}

clean:
	$(RM) *.class
