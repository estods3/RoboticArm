#include <Servo.h>
const int SERPin = 5;    //<-----corresponds to P1.1 on MSP430
const int RclockPin = 6; //<-----corresponds to P1.4 on MSP430
const int SclockPin = 7; //<-----corresponds to P1.5 on MSP430
const int timeDelay = 700; //MAKE THIS WHATEVER YOU WANT! (NOTE: the number is in milliseconds!!!)

Servo sClaw;
Servo sclawArm;
Servo sbaseArm;
Servo sbase;
int basePos;
int baseArmPos;
int clawArmPos;
void setup() {
  //NO NEED TO EDIT THIS STUFF!
  pinMode(SERPin, OUTPUT);      
  pinMode(RclockPin, OUTPUT);      
  pinMode(SclockPin, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);
  pinMode(14, OUTPUT);
  pinMode(15, OUTPUT);
  sClaw.attach(15); 
  sclawArm.attach(14);
  sbaseArm.attach(13);
  sbase.attach(12);
  sclawArm.write(90);
  sbaseArm.write(90);
  sbase.write(90);
  basePos = 50;
  baseArmPos = 0;
  clawArmPos = 0;
}

void writehigh(){
  //this function sets SclockPin to 0 so that it can write to the register
  //then writes a HIGH value to SER (Serial pin)
  //afterward, it writes a HIGH value to the SclockPin to finish writing
  digitalWrite(SclockPin, LOW);
  digitalWrite(SERPin, HIGH);
  digitalWrite(SclockPin, HIGH);
}
void writelow(){
  //this function sets SclockPin to 0 so that it can write to the register
  //then writes a LOW value to SER (Serial pin)
  //afterward, it writes a HIGH value to the SclockPin to finish writing
  digitalWrite(SclockPin, LOW);
  digitalWrite(SERPin, LOW);
  digitalWrite(SclockPin, HIGH);
}
void printLEDs(byte b){ 
    //just call the function with a byte variable parameter to print that data to the shift register!
    //using a bitwise AND operation (which looks like "&") we can determine whether each bit in "byte b" is a 1 or 0
    //if the condition in the "if((b & c) == c)" statement is true, then there is a 1 at the current position so light up the LED
    //else there must be a 0 so we turn off the LED!
    digitalWrite(RclockPin, LOW);
    for(byte c = 0b10000000; c > 0; c /= 2){
      if((b & c) == c){
        writehigh();
      } else{
        writelow();
      } 
    }
    digitalWrite(RclockPin, HIGH);
}
void claw(double percentClosed){
  int open = 45;
  int close = 30;
  int moveTo = open - (percentClosed/100)*(open-close);
  if(moveTo > sClaw.read()) {
      //GREEN
      byte t = 0b00001001;
      printLEDs(t);
      //YELLOW
      t = 0b00010010;
      delay(300);
      printLEDs(t);
      sClaw.write(moveTo);
      delay(300); 
      //RED
      t = 0b00100100;
      printLEDs(t);
      delay(300);
  } else {
      delay(200);
      sClaw.write(moveTo);
      delay(200);
  }
}

void clawArm(int pos){
  int time;
  if(pos != clawArmPos){
    if(pos > clawArmPos){
      time = (pos-clawArmPos)*1000;//some conversion
      sclawArm.write(150);
      delay(time);
    } else {
      sclawArm.write(80);
      delay(time); 
    }
  }
  clawArmPos = pos;
}
void baseArm(int posInSec){
  
   if(posInSec > 0){
    sbaseArm.write(100);
    delay(100);
    sbaseArm.write(150);
    delay(500);
    sbaseArm.write(100);
    delay(100);
    sbaseArm.write(90);
   } else {
    sbaseArm.write(80);
    delay(100);
    sbaseArm.write(30);
    delay(500);
    sbaseArm.write(80);
    delay(100); 
    sbaseArm.write(90); 
   } 
}
void base(int posInSec){
  
  if(posInSec > 0){
    sbase.write(180);
    delay(500);
  } else {
    sbase.write(80);
    delay(300);
  }
}
void loop() {
  byte stat = 0b0000000;
  //for(byte c = 0; c <= 9; c++){
    //delay(2000);
    //claw(25);    
    //delay(2000);
    //claw(50);
    //delay(2000);
    
    stat = 0b00111111;
    printLEDs(stat);
  //}
}
