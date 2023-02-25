package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 30, Math.toRadians(161.3125254775314), Math.toRadians(60), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-38.0, 64.0, Math.toRadians(-90.0)))
                                .lineTo(new Vector2d(-36.0, 20.0))
                                .lineToLinearHeading(new Pose2d(-40.0, 3.0, Math.toRadians(-12.5)))
                                .lineToLinearHeading(new Pose2d(-36.0, 12.0, Math.toRadians(-90.0)))
                                .lineToLinearHeading(new Pose2d(-12.0, 12.0, Math.toRadians(-90.0)))
                                .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}