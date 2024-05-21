@echo off
rem Fordítás
javac -cp "lib\json-20240303.jar;src" -encoding UTF-8 -d my_out\production\Logarlec src\AppMain.java

rem Ha a fordítás sikeres volt, akkor futtatás
if %errorlevel% == 0 (
    rem Futás
    java -cp "my_out\production\Logarlec;lib\json-20240303.jar" AppMain
) else (
    echo Fordítási hiba történt. A projekt nem futtatható.
)
