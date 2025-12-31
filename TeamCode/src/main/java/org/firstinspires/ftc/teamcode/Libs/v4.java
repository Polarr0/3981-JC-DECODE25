package org.firstinspires.ftc.teamcode.Libs;


import static org.firstinspires.ftc.teamcode.Libs.JCLibs.lerp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "v4", group = "leo")

public class v4 extends OpMode {

    DcMotor LeftM;
    DcMotor RightM;
    DcMotor BackM;
    DcMotor TL;
    DcMotor TR;
    DcMotor Arm;
    DcMotor Arm2;
    Servo svo;

    boolean strf;

    int roller = 0;
    int rollerpos;




    double lylrp = 0;
    double rxlrp = 0;

    @Override
    public void init() {
        telemetry.addData("Status: ","Initializing Hardware....");


        //                ---------Motor Initializations--------

        //L
        LeftM = hardwareMap.get(DcMotor.class,"L");
        LeftM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER );
        LeftM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        LeftM.setDirection(DcMotorSimple.Direction.REVERSE);

        //R
        RightM = hardwareMap.get(DcMotor.class,"R");
        RightM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //B
        BackM = hardwareMap.get(DcMotor.class,"B");
        BackM.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




        //Arm
        Arm = hardwareMap.get(DcMotor.class,"Arm");
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Arm2
        Arm2 = hardwareMap.get(DcMotor.class,"Arm2");
        Arm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);




        //tr
        TR = hardwareMap.get(DcMotor.class,"TR");
        TR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        TR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //tl
        TL = hardwareMap.get(DcMotor.class,"TL");
        TL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        TL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        //                ---------Servo Initializations--------

        svo = hardwareMap.get(Servo.class,"svo");





        telemetry.addData("Status: ","Initialization Complete");
        telemetry.update();
    }

    //telem
    public void telem(){

        telemetry.addData("ls: ", gamepad1.left_stick_y);
        telemetry.addData("rs: ", gamepad1.right_stick_x);
        telemetry.addData("lp ", LeftM.getPower());
        telemetry.addData("rp ", RightM.getPower());
        telemetry.addData("lylrp: ", lylrp);
        telemetry.addData("rxlrp: ", rxlrp);
        telemetry.addData("Arm Pos: ", Arm.getTargetPosition());
        telemetry.addData("Arm Pwr: ", Arm.getPower());
        telemetry.addData("triggers: ", gamepad1.right_trigger);
        telemetry.addData("R u strf?: ", strf);
        telemetry.addData("Back ", BackM.getPower());
        telemetry.addData("arm2 ", Arm2.getCurrentPosition());
        telemetry.addData("svo ", svo.getPosition());
        telemetry.addData("back pos: ", BackM.getCurrentPosition());
        telemetry.addData("b mode: ", BackM.getMode());
        telemetry.addData("b target: ", BackM.getTargetPosition());
        telemetry.addData("b zm: ", BackM.getZeroPowerBehavior());
        telemetry.addData("rollerpos: ", rollerpos);
        telemetry.addData("roller: ", roller);


        telemetry.update();
    }


    //arm
    public void arm(){

        //Arm code
        if (gamepad1.dpad_up) {

            Arm.setPower(-0.5);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        } else {
            gamepad1.dpad_up = false;
        }

        if (gamepad1.dpad_down) {

            Arm.setPower(0.5);
            Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        } else {
            gamepad1.dpad_up = false;
        }
    }

    public void turn(){

        if (gamepad1.dpad_left) {
            LeftM.setPower(.5);
            RightM.setPower(-.5);
            BackM.setPower(-.6);
            BackM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        } else if (gamepad1.dpad_right) {
            LeftM.setPower(-.5);
            RightM.setPower(.5);
            BackM.setPower(.6);
            BackM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }



    }

    //liffup
    public void liffup(int pos){
        Arm2.setTargetPosition(pos-100);
        Arm2.setPower(.2);

        if(gamepad1.square) {
            svo.setPosition(.5);
        }else{
            svo.setPosition(0);
        }

    }

    public void backcode(){
        boolean test

        if (gamepad1.triangle)

        int bpos = BackM.getCurrentPosition();
        int quotient = Math.abs(bpos/58);




        if (bpos % 58 == 0){
            roller = quotient;
            rollerpos = bpos;

        }

        if (BackM.getPower() == 0){
            BackM.setTargetPosition(rollerpos);
            BackM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BackM.setPower(0.1);

        }
    }



    @Override
    public void loop() {
        lylrp = lerp(gamepad1.left_stick_y,lylrp,0.5);
        rxlrp = lerp(gamepad1.right_stick_x,rxlrp,0.5);

        strf = gamepad1.right_stick_x != 0;

        backcode();


        if (strf) {
            LeftM.setPower(rxlrp);
            RightM.setPower(-rxlrp);
            BackM.setPower(rxlrp);
            BackM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        }else if (!strf) {
            //Forward and Reverse

            LeftM.setPower(lylrp);
            RightM.setPower(lylrp);
        }

        BackM.setPower(0);

        turn();

        Arm.setPower(0);
        arm();

        if (gamepad1.right_trigger > 0) {
            TR.setPower(gamepad1.right_trigger);
            TL.setPower(gamepad1.right_trigger);

        } else {
            TR.setPower(0);
            TL.setPower(0);
        }

        if (gamepad1.triangle) {

            backcode();

        }


        telem();

    }

}
