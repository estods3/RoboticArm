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
  //--------------------------EDIT AT START-------------------------------
  baseArmPos = 50; //0 is all the way down, and 100% is fully extended
  clawArmPos = 0; //0 is facing completely upward and 100% is fully down
  //----------------------------------------------------------------------
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
  int open = 0;
  int close = 90;
  int moveTo = (percentClosed/100)*(close);
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
      printLEDs(0b00000000);
      delay(200);
      sClaw.write(moveTo);
      delay(200);
  }
}

void clawArm(int pos){
  int time;
  if(pos != clawArmPos){
    if(pos > clawArmPos){
      time = (  (double)(pos-clawArmPos)/10)*1000;
      sclawArm.write(110);
      delay(time);
    } else {
      time = -1*((double)(pos-clawArmPos)/10)*1000;
      sclawArm.write(70);
      delay(time); 
    }
  }
  sclawArm.write(90);
  clawArmPos = pos;
}
void baseArm(int pos){
  int time;
   if(pos > baseArmPos){
    time = ((double)(pos-baseArmPos)/20)*1000;
    sbaseArm.write(70);
    delay(time);
   } else {
    time = -1*((double)(pos-baseArmPos)/20)*1000;
    sbaseArm.write(110);
    delay(time);
   } 
   sbaseArm.write(90);
   baseArmPos = pos;
}
void base(double percentTurn){
  int left = 180;
  int right = 0;
  int center = 90;
  int moveTo = (percentTurn/(double)100)*(left-right);
  sbase.write(moveTo);
}

void loop() {
  //----------START PROGRAM----------
  baseArm(100);
  delay(100);
  clawArm(50);
  delay(100);
  claw(100);
  delay(200);
  
  //----------STOP PROGRAM----------


  
  //----------HOME POSITION----------
  clawArm(0);
  baseArm(20);
  claw(0);
  base(50);
  //-------------FINISHED------------
  while(1){
    printLEDs(0b00111111);
    delay(100);
    printLEDs(0b00000000);
    delay(100);
  }
}
