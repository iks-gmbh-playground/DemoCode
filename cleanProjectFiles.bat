echo off

del .project /a /s
del .classpath /a /s

FOR /d /r . %%d IN (target) DO @IF EXIST "%%d" rd /s /q "%%d"
FOR /d /r . %%d IN (.settings) DO @IF EXIST "%%d" rd /s /q "%%d"

rem ATTENTION: delete bin folder manually because for java9modules the bin dir must not be deleted !

