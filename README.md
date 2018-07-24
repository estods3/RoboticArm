# 4 Degree of Freedom (DoF) Open-Loop Robotic Arm

![Alt text](/Documentation/clawWithSectionLabels.png?raw=true "Robotic Arm Sections")

This repository contains code and documentation for a robotic arm uses 4 servo motors to drive 4 degrees of motion. Using Legos, an MSP430 and a perf board circuit, I was able to create a variety of programs that could control the arm.

This documentation illustrates the process of how I designed the arm, circuit, and program to run the arm.

## Components and Software
Legos(basic blocks, gears, shafts, miscellaneous mechanical parts)
2 positional Servos (# of each depends on your design)
2 continuous rotation servos (# of each depends on your design)
LEDs (2 green, 2 yellow, 2 red)
Appropriate resistors (for LEDs)
A “SN74HC595n” shift register (used to serially control LEDs)
A breadboard and/or perfboard
A set of solid-core wire and male/female jumper wire
A TI MSP430G2553 or Arduino
Energia software: http://energia.nu/download/ (for TI microcontrollers)

## Hardware

This design included a “Base” that can rotate the rest of the arm 180 degrees. Next, a “Base Arm” that allows the rest of the arm to extend back and forth. Finally, the “Claw Arm” servo can raise and lower the “Claw” so that the Claw can open and close to grab objects. The Base Arm has a little less than 180 degrees of rotation while the Claw Arm has almost 360 degrees of freedom. Between these three servo motors, the Claw can reach almost any point in space within a 2 foot radius of the Base. The Claw has a range of around 40 degrees which means it can grab an object as large as 3 inches across.

A simple way to move each joint would be to use positional servo motors. However, due to the material (Legos) that I was using for my arm, I felt that this route would put too much stress on the joint motors and the servos wouldn’t be able to hold the weight. For my design, I chose to use a Lego gear boxes with a worm and worm-gear to drive 4 extrusions (2 on each side) making up the "Base Arm" section of the robot. A worm and worm-gear can't be operated in reverse, so the position stays locked in place. I used the same component for the Claw Arm section as well. However, the gear boxes require a continous input since the input shaft must rotate dozens of times to get a rotation on the output. A positional servo motor won't work here because it can't spin that many times consecutively. 

![Alt text](/Documentation/closeupOfPerfBoard.jpg?raw=true "Robotic Arm Main Circuit")

A shift register was used to serially control 6 LEDs. This is sometimes useful when using a small microcontroller with limited GPIO.
The 6 LEDs are green, yellow and red on the two sides of the arm that lite up when the claw is closing. I created a compact version of the circuit below using perfboard.

![Alt text](/Documentation/schematic visual.png?raw=true "Robotic Arm Schematic")

For additional compactness, I also soldered a row of male header pins to the perfboard for the Base Arm, Claw Arm, and Claw servo motors. These motors each have three wires which are subject to getting tangled in the motion of the arm. With this compact circuit, data and power wires for the servos can be stored in the plastic conduit. As for the Base servo, it made more sense for the cable to fixed directly to the USB power cable with a splitter so the data wire could be attached to the microcontroller.

## Software

The software primarily uses open-loop control, which is less than ideal for robotics. If I were to redo the project, I would add feedback sensors such as rotary encoders to ensure that the robot was reaching the requested position for the joints using continously rotating servo motors.

Calibration is entered at the start of the program indicating the position of the BaseArm and ClawArm as a percent of their range. This worked well, but if a program was run repeatedly, the arm was subject to skew as is common in open-loop control.

Additional functionality for "homing" and LED animations are also present. Ideally, the main body of the code can be adjusted to control the robot through a variety of tasks followed by the homing command. When homing is completed, the red, yellow, and green LEDs on each side begin to flash on and off to indicate that the program has finished.
