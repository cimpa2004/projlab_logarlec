@echo off

rem Fordítás
javac -cp "lib\json-20240303.jar;src" -encoding UTF-8 -d my_out\production\Logarlec src\AppMain.java

rem Ellenőrizd, hogy a fordítás sikeres volt-e
if %errorlevel% neq 0 (
    echo Fordítási hiba történt. A .jar fájl nem lett létrehozva.
    exit /b %errorlevel%
)

rem MANIFEST.MF fájl létrehozása
echo Manifest-Version: 1.0 > manifest.mf
echo Main-Class: AppMain >> manifest.mf

rem DemoMap.json másolása egy könyvtárral feljebb
copy DemoMap.json my_out\production\Logarlec\.. /y

rem .jar fájl létrehozása
jar cfm Logarlec.jar manifest.mf -C my_out\production\Logarlec .

rem Ellenőrizd, hogy a .jar fájl sikeresen létrejött-e
if exist Logarlec.jar (
    echo A Logarlec.jar fájl sikeresen létrejött.
) else (
    echo Hiba történt a Logarlec.jar fájl létrehozása során.
)

rem Takarítás: a manifest.mf fájl törlése, ha szükséges
del manifest.mf
