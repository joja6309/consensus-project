@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  com.consensus.kafka startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and COM_CONSENSUS_KAFKA_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\com.consensus.kafka.jar;%APP_HOME%\lib\jgrapht-core-1.1.0.jar;%APP_HOME%\lib\com.consensus.utils.jar;%APP_HOME%\lib\spring-kafka-2.1.4.RELEASE.jar;%APP_HOME%\lib\kafka-clients-1.0.0.jar;%APP_HOME%\lib\assertj-core-3.9.1.jar;%APP_HOME%\lib\mockito-core-1.10.19.jar;%APP_HOME%\lib\jackson-databind-2.9.0.jar;%APP_HOME%\lib\spring-context-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-messaging-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-tx-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-retry-1.2.2.RELEASE.jar;%APP_HOME%\lib\lz4-java-1.4.jar;%APP_HOME%\lib\snappy-java-1.1.4.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\hamcrest-core-1.1.jar;%APP_HOME%\lib\objenesis-2.1.jar;%APP_HOME%\lib\jackson-annotations-2.9.0.jar;%APP_HOME%\lib\jackson-core-2.9.0.jar;%APP_HOME%\lib\spring-aop-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-beans-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-expression-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-core-5.0.4.RELEASE.jar;%APP_HOME%\lib\spring-jcl-5.0.4.RELEASE.jar

@rem Execute com.consensus.kafka
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %COM_CONSENSUS_KAFKA_OPTS%  -classpath "%CLASSPATH%" com.consensus.kafka.KafkaProducerImpl %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable COM_CONSENSUS_KAFKA_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%COM_CONSENSUS_KAFKA_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
