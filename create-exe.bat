@echo off
title PENDO - Generador de EXE
echo ========================================
echo    PENDO - Generador de Ejecutable
echo    Creando archivo .exe...
echo ========================================
echo.

REM Verificar Java 17+
java --version | findstr "17\|18\|19\|20\|21" >nul
if %errorlevel% neq 0 (
    echo ERROR: Se requiere Java 17 o superior para jpackage
    echo Tu versión actual es:
    java --version
    pause
    exit /b 1
)

echo Creando directorio de salida...
if exist "dist" rmdir /s /q dist
mkdir dist

echo.
echo Compilando aplicación...
call mvn clean compile

if %errorlevel% neq 0 (
    echo ERROR: Falló la compilación
    pause
    exit /b 1
)

echo.
echo Creando JAR ejecutable...
call mvn package

if %errorlevel% neq 0 (
    echo ERROR: Falló la creación del JAR
    pause
    exit /b 1
)

echo.
echo Generando ejecutable con jpackage...
jpackage ^
    --input target ^
    --name "PENDO-TaskOrganizer" ^
    --main-jar "todo-list-app-1.0-SNAPSHOT.jar" ^
    --main-class "org.uees.view.MainApp" ^
    --type exe ^
    --dest dist ^
    --app-version "1.0" ^
    --description "PENDO Task Organizer - Organizador de Tareas" ^
    --vendor "UEES" ^
    --copyright "Copyright 2025 UEES"

if %errorlevel% neq 0 (
    echo ERROR: jpackage falló
    echo Verifica que todas las dependencias estén correctas
    pause
    exit /b 1
)

echo.
echo ========================================
echo ¡ÉXITO! Ejecutable creado en:
echo %cd%\dist\PENDO-TaskOrganizer.exe
echo ========================================
pause