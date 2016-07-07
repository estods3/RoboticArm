#include <Servo.h>

Servo s;
void setup()
{
  s.attach(4);
  // put your setup code here, to run once:
  
}

void loop()
{
  s.write(100);
  delay(200);
  
  s.write(150);
  delay(2000);
  
  s.write(100);
  delay(200);
  
  s.write(90);
  
  s.write(80);
  delay(200);
  
  s.write(30);
  delay(2000);
  
  s.write(80);
  delay(200); 
  
  s.write(90); 
  // put your main code here, to run repeatedly:
  
}
