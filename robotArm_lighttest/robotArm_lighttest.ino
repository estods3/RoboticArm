#include <Servo.h>
const int SERPin = 3;    //<-----corresponds to P1.1 on MSP430
const int RclockPin = 6; //<-----corresponds to P1.4 on MSP430
const int SclockPin = 7; //<-----corresponds to P1.5 on MSP430
const int timeDelay = 700; //MAKE THIS WHATEVER YOU WANT! (NOTE: the number is in milliseconds!!!)

Servo sclawArm;
Servo sClaw;
void setup() {
  //NO NEED TO EDIT THIS STUFF!
  pinMode(SERPin, OUTPUT);      
  pinMode(RclockPin, OUTPUT);      
  pinMode(SclockPin, OUTPUT);     
  //pinMode(one, OUTPUT);      
  //pinMode(two, OUTPUT);      
  //pinMode(three, OUTPUT);
  //pinMode(four, OUTPUT); 
  sClaw.attach(15); 
  sclawArm.attach(14);
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
void claw(byte b, int posInSec, int inDirection){
  if(inDirection == 1) {
      //GREEN
      byte t = b | 0b00001001;
      delay(500);
      printLEDs(t);
      //YELLOW
      t = b | 0b00010010;
      delay(200);
      printLEDs(t);
      sClaw.write(55);
      delay(200); 
      //RED
      t = b | 0b00100100;
      printLEDs(t);
  } else {
      delay(200);
      sClaw.write(20);
      delay(500);
  }
}

void clawArm(int posInSec, int inDirection){
  if(inDirection == 1){
    sclawArm.write(100);
    delay(200);
  
    sclawArm.write(150);
    delay(2000);
  
    sclawArm.write(100);
    delay(200);
  
    sclawArm.write(90);
  } else {
  
    sclawArm.write(80);
    delay(200);
  
    sclawArm.write(30);
    delay(2000);
  
    sclawArm.write(80);
    delay(200); 
  
    sclawArm.write(90); 
  }
}
void base(byte b, int posInSec, int inDirection){
  byte t = b | 0b10000000;
  delay(500);
  printLEDs(t);
}
/*
void printCount(byte b){
  byte t = b & 0b00001000;
  if(t == 0b00001000){
    digitalWrite(one, HIGH);    
  } else{
    digitalWrite(one, LOW);
  }
  t = b & 0b00000100;
  if(t == 0b00000100){
    digitalWrite(two, HIGH);    
  } else{
    digitalWrite(two, LOW);
  }   
  t = b & 0b00000010;
  if(t == 0b00000010){
    digitalWrite(three, HIGH);    
  } else{
    digitalWrite(three, LOW);
  }
  t = b & 0b00000001;
  if(t == 0b00000001){
    digitalWrite(four, HIGH);    
  } else{
    digitalWrite(four, LOW);
  }     
}
*/
void loop() {
  byte stat = 0b00000000;
  for(byte c = 0; c <= 9; c++){
    delay(1000);
    base(stat, 0, 0);
    delay(2000);
    clawArm(0,1);
    claw(stat, 0, 1);    
    clawArm(0,0);
    claw(stat, 0, 0);
 }
}
