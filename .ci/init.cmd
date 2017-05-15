REM Maintainer Ce Gao(gaocegege) <github.com/gaocegege>

REM # Paths
REM # Those paths are not needed when building runner.jar,
REM # but will be used in the future to package the mode.
set modes="c:\\mock-user\\modes"
set executable="c:\\mock-user\\Processing"
REM # Those paths are important to build runner.jar.
set processing="c:\\projects\\processing-3.3"
set core="c:\\projects\\processing-3.3\\core\\library\\core.jar"
set pde="c:\\projects\\processing-3.3\\lib\\pde.jar"
set renjin="lib\\renjin-script-engine-0.8.2194-jar-with-dependencies.jar"

set processingr="c:\projects\Processing.R"

cd %processingr%
copy build.xml.template build.xml
REM # Interpret config template.
echo "Inject the config to build.xml.template."
perl -i.bak -pe "s|\@\@modes\@\@|%modes%|g" build.xml && del build.xml.bak
perl -i.bak -pe "s|\@\@executable\@\@|%executable%|g" build.xml && del build.xml.bak
perl -i.bak -pe "s|\@\@processing\@\@|%processing%|g" build.xml && del build.xml.bak
perl -i.bak -pe "s|\@\@core\@\@|%core%|g" build.xml && del build.xml.bak
perl -i.bak -pe "s|\@\@pde\@\@|%pde%|g" build.xml && del build.xml.bak
perl -i.bak -pe "s|\@\@renjin\@\@|%renjin%|g" build.xml && del build.xml.bak
