package com.example.pqi13.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class OpencvFeatureSelection extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    public static final String TAG = "feature_selection";
    private CameraBridgeViewBase cameraBridgeViewBase;


    private Mat                    mRgba;
    private Mat                    mGrayMat;

    Mat descriptors ;
    List<Mat> descriptorsList;

    FeatureDetector featureDetector;
    MatOfKeyPoint keyPoints;
    DescriptorExtractor descriptorExtractor;
    DescriptorMatcher descriptorMatcher;


    static {
        if (!OpenCVLoader.initDebug()) {
            Log.wtf(TAG, "OpenCV failed to load!");
        }
    }



    //private JavaCameraView cameraView;

    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    cameraBridgeViewBase.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_feature_selection_layout);
        cameraBridgeViewBase = (CameraBridgeViewBase) findViewById(R.id.camera_view_2);
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
    }




    @Override
    public void onResume(){
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, loaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null)
            cameraBridgeViewBase.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

        mRgba = new Mat();
        mGrayMat = new Mat();
        featureDetector=FeatureDetector.create(FeatureDetector.SIFT);
        descriptorExtractor=DescriptorExtractor.create(DescriptorExtractor.SURF);
        descriptorMatcher=DescriptorMatcher.create(6);
        keyPoints = new MatOfKeyPoint();
        descriptors = new Mat();

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        final Mat rgba = inputFrame.rgba();

        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY);
        featureDetector.detect(rgba, keyPoints);
        Features2d.drawKeypoints(rgba, keyPoints, rgba);
        return rgba;
    }





}
