package org.firstinspires.ftc.teamcode.Libs;


import static org.firstinspires.ftc.teamcode.Libs.JCLibs.lerp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "v3", group = "leo")

public class v3 extends OpMode {

    DcMotor LeftM;
    DcMotor RightM;
    DcMotor BackM;
    DcMotor TL;
    DcMotor TR;
    DcMotor Arm;

    double lylrp = 0;
    double rxlrp = 0;


    public void armup(int pos){
        if (gamepad1.dpad_up) {

            Arm.setPower(-0.5);
            Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setTargetPosition(pos - 150);

        } else {
            Arm.setTargetPosition(0);
        }

    }

    public void armdwn(int pos){
        if(gamepad1.dpad_down){
            Arm.setPower(0.3);
            Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Arm.setTargetPosition(pos+100);

        }else {
            Arm.setTargetPosition(0);
            gamepad1.dpad_down = false;
        }


    }


    @Override
    public void init() {
        telemetry.addData("Status: ","Initializing Hardware....");

        //                ---------Motor Initializations--------

        //L
        LeftM = hardwareMap.get(DcMotor.class,"L");
        LeftM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        LeftM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftM.setDirection(DcMotorSimple.Direction.REVERSE);

        //R
        RightM = hardwareMap.get(DcMotor.class,"R");
        RightM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //B
        BackM = hardwareMap.get(DcMotor.class,"B");
        BackM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Arm
        Arm = hardwareMap.get(DcMotor.class,"Arm");
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //tr
        TR = hardwareMap.get(DcMotor.class,"TR");
        TR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //tl
        TL = hardwareMap.get(DcMotor.class,"TL");
        TL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);




        telemetry.addData("Status: ","Initialization Complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        lylrp = lerp(gamepad1.left_stick_y,lylrp,0.5);

        if (gamepad1.right_stick_x>.5){
            rxlrp = lerp(gamepad1.right_stick_x,rxlrp,0.5);

        }


        Arm.setPower(0);




        //Forward and Reverse

        LeftM.setPower(lylrp);
        RightM.setPower(lylrp);


//        //Right and Left
//        LeftM.setPower(rxlrp);
//        RightM.setPower(rxlrp);

        //Turn

        if (gamepad1.dpad_left){
            LeftM.setPower(.5);
            RightM.setPower(-.5);
            BackM.setPower(.5);
            BackM.resetDeviceConfigurationForOpMode();
            BackM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        }else{
            BackM.setPower(0);
        }

        if (gamepad1.dpad_right){
            LeftM.setPower(-.5);
            RightM.setPower(.5);
            BackM.setPower(-.5);
            BackM.resetDeviceConfigurationForOpMode();
            BackM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }else {
            BackM.setPower(0);
        }





        //Arm code
        armup(0 );
        armdwn(0 );





        if (gamepad1.right_trigger > 0) {
            TR.setPower(gamepad1.right_trigger);
            TL.setPower(gamepad1.right_trigger);

        } else {
            TR.setPower(0);
            TL.setPower(0);
        }

        telemetry.addData("ls: ", gamepad1.left_stick_y);
        telemetry.addData("rs: ", gamepad1.right_stick_x);
        telemetry.addData("lylrp: ", lylrp);
        telemetry.addData("rxlrp: ", rxlrp);
        telemetry.addData("dpd d: ", gamepad1.dpad_down);
        telemetry.addData("dpd u: ", gamepad1.dpad_up);
        telemetry.addData("Arm Pos: ", Arm.getTargetPosition());
        telemetry.addData("Arm Pwr: ", Arm.getPower());
        telemetry.addData("triggers: ", gamepad1.right_trigger);
        telemetry.update();
    }

}
