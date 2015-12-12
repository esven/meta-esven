<?php

$db = new SQLite3("/tmp/openhr20.sqlite");
$TIMEZONE="Europe/Berlin";
$RRD_ENABLE=true;
$PLOTS_DIR = "plots";
$RRD_DAYS = array (3, 7, 30, 90);

  // translation table for valve names
   $room_name = array (
    0x01 => 'Schlafzimmer',
    0x02 => 'Badezimmer',
    0x03 => 'Kinderzimmer',
    0x04 => 'Wohnzimmer Links',
    0x05 => 'Wohnzimmer Mitte',
    0x06 => 'Wohnzimmer Rechts',
  ); 

  // translation table for timers name (weekdays)
  $timer_names =  array (
    'Woche',
    'Montag',
    'Dienstag',
    'Mittwoch',
    'Donnerstag',
    'Freitag',
    'Samstag',
    'Sonntag'
  );

  // symbols for 4 temperature mode
  // unicode version with nice moon/sun symbols, have problem on mobile Opera browser
   $symbols = array (
      'x',		//off
      '&#x263e;',	//Night
      '&#x2600;',	//Day
      '&#x263c;		//Comfort
    '); 

  $refresh_value=15; // refresh time for command queue pending wait 
  $chart_hours = 48; // chart contain values from last 12 hours
  $warning_age = 8*60; // maximum data age for warning
  $error_age = 20*60; // maximum data age for error
  
?>
