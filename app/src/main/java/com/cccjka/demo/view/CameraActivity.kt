package com.cccjka.demo.view
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.SurfaceView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.cccjka.demo.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity: AppCompatActivity() {

    /**  官方示例, 摄像头预览画面以及录像  **/

    private val TAG = CameraActivity::class.java.name

    private lateinit var btn_capture:Button
    private lateinit var previewView: PreviewView
    private lateinit var tv_status: TextView

    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        initAll()
    }

    private fun initAll(){
        intView()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun intView(){
        btn_capture = findViewById(R.id.btn_capture)
        previewView = findViewById(R.id.viewFinder)
        tv_status = findViewById(R.id.tv_recording_status)
    }

    /** 权限申请 */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "申请权限失败", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    /** 开启预览 */
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // 相机生命周期与进程生命周期绑定
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // 设置预览视图
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                .build()
            videoCapture = VideoCapture.withOutput(recorder)

            imageCapture = ImageCapture.Builder()
                .build()
            //默认摄像头，一般有前置和后置，如果打开软件识别不到那么可以设置成DEFAULT_FRONT_CAMERA看看能不能调用前置摄像头
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                btn_capture.setOnClickListener{
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                    takePhoto()
                }
                btn_capture.setOnLongClickListener{
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, videoCapture)
                    captureVideo()
                    true
                }
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    /** 拍照 */
    private fun takePhoto() {
        // 获取变量imageCapture，如果为空则直接返回
        val imageCapture = imageCapture ?: return

        // 创建相片时间戳
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + "Photo")
            }
        }

        // 创建图片输出的路径
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // 创建拍照后的监听
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    // 录像的使用
    private fun captureVideo() {
        val videoCapture = this.videoCapture ?: return

        val curRecording = recording
        curRecording?.let {
            // 停止当前正在进行的录像
            curRecording.stop()
            recording = null
            return
        }

        // 创建新的录像信息并开始录像
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + "Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()
        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .apply {
                if (PermissionChecker.checkSelfPermission(this@CameraActivity,
                        Manifest.permission.RECORD_AUDIO) == PermissionChecker.PERMISSION_GRANTED)
                {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when(recordEvent) {
                    is VideoRecordEvent.Start -> {
                        tv_status.text = "开始录像..."
                    }
                    is VideoRecordEvent.Finalize -> {
                        if (!recordEvent.hasError()) {
                            val msg = "Video capture succeeded: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }
                        tv_status.text = ""
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1);
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

}

/**
* 调用系统相机拍照后返回图片Uri
 *
 * companion object {//控制两种打开方式
 *     val TAKE_PHOTO = 1
 *
 *         val CHOOSE_PHOTO = 2
 *
 *     }
 *
 *
 *     var imageUri: Uri? = null
 *
 * btn_capture.setOnClickListener {
 *
 *             // 1. 创建File对象，用于存储拍照后的图片
 *             val outputImage = File(externalCacheDir, "output_image.jpg")
 *             try {
 *                 if (outputImage.exists()) {
 *                     outputImage.delete()
 *                 }
 *                 outputImage.createNewFile()
 *             } catch (e: IOException) {
 *                 e.printStackTrace()
 *             }
 *
 * //2.
 *             if (Build.VERSION.SDK_INT < 24) {
 *                 imageUri = Uri.fromFile(outputImage)
 *             } else {
 *                 imageUri = FileProvider.getUriForFile(
 *                     this@CameraActivity,
 *                     "com.cccjka.demo.fileprovider",//定义唯一标识，关联后面的内容提供器
 *                     outputImage
 *                 )
 *             }
 *             // 3. 启动相机程序
 *             val intent = Intent("android.media.action.IMAGE_CAPTURE")
 *             intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
 *             startActivityForResult(intent, TAKE_PHOTO)
 *
 *         }
 *
 *          //4. 执行回调
 *     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
 *         super.onActivityResult(requestCode, resultCode, data)
 *         when (requestCode) {
 *             TAKE_PHOTO -> if (resultCode == RESULT_OK) {
 *                 try {
 *                     // 将拍摄的照片显示出来
 *                     val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!))
 *                     picture.setImageBitmap(bitmap)
 *                 } catch (e: Exception) {
 *                     e.printStackTrace()
 *                 }
 *             }
 *
 *         }
 *     }
*
* */