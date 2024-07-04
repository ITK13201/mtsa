rem ===
rem EXPERIMENTS FOR MTSA MACHINE LEARNING
rem ENCODING: UTF-8
rem NEW LINE CODE: CRLF
rem ===

@echo off
chcp 65001

setlocal

rem =========
rem SETTINGS
rem =========
rem java
set MEMORY_FLAG=-Xmx225G
set VERSION=PCS_MachineLearning_v0.0.3
set TARGET=mtsa-%VERSION%.jar
rem mtsa
set MTSA_INPUT_DIR=input
set MTSA_OUTPUT_DIR=output
set MTSA_TARGET=TraditionalController
set MTSA_LOG_DIR=log
rem sleep (ms)
set SLEEP_TIME=10000

rem ============
rem INPUT FILES
rem ============
set AT[0]=AT.lts
set AT[1]=AT（2, 2）.lts
set AT[2]=AT（2, 3）.lts
set AT[3]=AT（2, 4）.lts
set AT[4]=AT（2, 5）.lts
set AT[5]=AT（3, 3）.lts
set AT[6]=AT（3, 4）.lts
set AT[7]=AT（3, 5）.lts
set AT[8]=AT（4, 4）.lts
set AT[9]=AT（4, 5）.lts
set AT[10]=AT（5, 5）.lts
set AT[11]=AT（5, 10）.lts
set ART_GALLERY[0]=ArtGallery_Example.lts
set ART_GALLERY[1]=ArtGallery（N, 2 room）.lts
set ART_GALLERY[2]=ArtGallery（N, 3 room）.lts
set ART_GALLERY[3]=ArtGallery（N, 4 room）.lts
set ART_GALLERY[4]=ArtGallery（N, 5 room）.lts
set ART_GALLERY[5]=ArtGallery（N, 6 room）.lts
set ART_GALLERY[6]=ArtGallery（N, 7 room）.lts
set ART_GALLERY[7]=ArtGallery（N, 8 room）.lts
set ART_GALLERY[8]=ArtGallery（N, 9 room）.lts
set ART_GALLERY[9]=ArtGallery（N, 10 room）.lts
set ART_GALLERY[10]=ArtGallery（S, 5 people）.lts
set ART_GALLERY[11]=ArtGallery（S, 6 people）.lts
set ART_GALLERY[12]=ArtGallery（S, 7 people）.lts
set ART_GALLERY[13]=ArtGallery（S, 8 people）.lts
set ART_GALLERY[14]=ArtGallery（S, 9 people）.lts
set ART_GALLERY[15]=ArtGallery（S, 10 people）.lts
set BW[0]=BW.lts
set BW[1]=BW（2, 2）.lts
set BW[2]=BW（2, 3）.lts
set BW[3]=BW（2, 4）.lts
set BW[4]=BW（2, 5）.lts
set BW[5]=BW（3, 2）.lts
set BW[6]=BW（3, 3）.lts
set BW[7]=BW（3, 4）.lts
set BW[8]=BW（3, 5）.lts
set BW[9]=BW（4, 2）.lts
set BW[10]=BW（4, 3）.lts
set BW[11]=BW（4, 4）.lts
set BW[12]=BW（4, 5）.lts
set BW[13]=BW（5, 2）.lts
set BW[14]=BW（5, 3）.lts
set BW[15]=BW（5, 4）.lts
set BW[16]=BW（5, 5）.lts
set CM[0]=CM.lts
set CM[1]=CM（2, 2）.lts
set CM[2]=CM（2, 3）.lts
set CM[3]=CM（2, 4）.lts
set CM[4]=CM（2, 5）.lts
set CM[5]=CM（3, 2）.lts
set CM[6]=CM（3, 3）.lts
set CM[7]=CM（3, 4）.lts
set CM[8]=CM（3, 5）.lts
set CM[9]=CM（4, 2）.lts
set CM[10]=CM（4, 3）.lts
set CM[11]=CM（4, 4）.lts
set CM[12]=CM（4, 5）.lts
set CM[13]=CM（5, 2）.lts
set CM[14]=CM（5, 3）.lts
set CM[15]=CM（5, 4）.lts
set CM[16]=CM（5, 5）.lts
set KIVA_SYSTEM[0]=KIVA_system（N, 2 robot）.lts
set KIVA_SYSTEM[1]=KIVA_system（N, 3 robot）.lts
set KIVA_SYSTEM[2]=KIVA_system（N, 4 robot）.lts
set KIVA_SYSTEM[3]=KIVA_system（S, 5 pod）.lts
set KIVA_SYSTEM[4]=KIVA_system（S, 10 pod）.lts
set KIVA_SYSTEM[5]=KIVA_system（S, 20 pod）.lts
set KIVA_SYSTEM[6]=KIVA_system（S, 30 pod）.lts


rem =============
rem EXECUTE MTSA
rem =============
rem AT
for /L %%i in (0,1,11) do (
    call echo %date% %time% "Started to execute %%AT[%%i]%%..."
    call java %MEMORY_FLAG% -jar %TARGET% compose -f "%MTSA_INPUT_DIR%\%%AT[%%i]%%" -t %MTSA_TARGET% -o %MTSA_OUTPUT_DIR% > "%MTSA_LOG_DIR%\%%AT[%%i]%%.log"
    call echo %date% %time% "Finished to execute %%AT[%%i]%%."
    sleep -m %SLEEP_TIME%
)
rem ART_GALLERY
for /L %%i in (0,1,15) do (
    call echo %date% %time% "Started to execute %%ART_GALLERY[%%i]%%..."
    call java %MEMORY_FLAG% -jar %TARGET% compose -f "%MTSA_INPUT_DIR%\%%ART_GALLERY[%%i]%%" -t %MTSA_TARGET% -o %MTSA_OUTPUT_DIR% > "%MTSA_LOG_DIR%\%%ART_GALLERY[%%i]%%.log"
    call echo %date% %time% "Finished to execute %%ART_GALLERY[%%i]%%."
    sleep -m %SLEEP_TIME%
)
rem BW
for /L %%i in (0,1,16) do (
    call echo %date% %time% "Started to execute %%BW[%%i]%%..."
    call java %MEMORY_FLAG% -jar %TARGET% compose -f "%MTSA_INPUT_DIR%\%%BW[%%i]%%" -t %MTSA_TARGET% -o %MTSA_OUTPUT_DIR% > "%MTSA_LOG_DIR%\%%BW[%%i]%%.log"
    call echo %date% %time% "Finished to execute %%BW[%%i]%%."
    sleep -m %SLEEP_TIME%
)
rem CM
for /L %%i in (0,1,16) do (
    call echo %date% %time% "Started to execute %%CM[%%i]%%..."
    call java %MEMORY_FLAG% -jar %TARGET% compose -f "%MTSA_INPUT_DIR%\%%CM[%%i]%%" -t %MTSA_TARGET% -o %MTSA_OUTPUT_DIR% > "%MTSA_LOG_DIR%\%%CM[%%i]%%.log"
    call echo %date% %time% "Finished to execute %%CM[%%i]%%..."
    sleep -m %SLEEP_TIME%
)
rem KIVA_SYSTEM
for /L %%i in (0,1,6) do (
    call echo %date% %time% "Started to execute %%KIVA_SYSTEM[%%i]%%..."
    call java %MEMORY_FLAG% -jar %TARGET% compose -f "%MTSA_INPUT_DIR%\%%KIVA_SYSTEM[%%i]%%" -t %MTSA_TARGET% -o %MTSA_OUTPUT_DIR% > "%MTSA_LOG_DIR%\%%KIVA_SYSTEM[%%i]%%.log"
    call echo %date% %time% "Finished to execute %%KIVA_SYSTEM[%%i]%%."
    sleep -m %SLEEP_TIME%
)

endlocal
