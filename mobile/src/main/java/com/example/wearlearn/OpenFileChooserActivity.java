package com.example.wearlearn;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

import AlertWindows.AlertDialogActivity;
import Interfaces.FileUploadService;
import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import wrappers.RetrofitWrapper;

/**
 * Created by Michał on 4/26/2017.
 */

public class OpenFileChooserActivity extends AppCompatActivity {


    @InjectView(R.id.choose_file)
    TextView _chooseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file_chooser);
        ButterKnife.inject(this);

        _chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewChooserFile();
            }
        });

    }

    public void viewChooserFile() {
        new FileChooserActivity(this).setFileListener(new FileChooserActivity.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                // do something with the file

                if(FilenameUtils.getExtension(file.getAbsolutePath()).equals("csv")) {
                    //file is .csv sent file to server


                    //TODO function send



                }else{
                    //file isnt .csv sent file to server
                    AlertDialogActivity.alertChooseFailFile(OpenFileChooserActivity.this);
                }
            }}).showDialog();
        }



    private void uploadFile(File orginalFile) {

        String descriptionString = FilenameUtils.removeExtension(orginalFile.getName());

        //File orginalFile = new File(path);

        RequestBody descriptionPart =
                RequestBody.create(
                        MultipartBody.FORM, descriptionString);

        RequestBody filePart =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(Uri.fromFile(orginalFile))),
                        orginalFile
                );

        MultipartBody.Part file = MultipartBody.Part.createFormData("csv", orginalFile.getName(), filePart);

        //create Retrofit instance
        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://wl-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =  builder.build();*/

        RetrofitWrapper retro = RetrofitWrapper.getSingleton();

        //get client and call object for the request
        FileUploadService client = retro.getRetrofit().create(FileUploadService.class);

        //finally execute the reqest
        Call<ResponseBody> call = client.upload(descriptionPart, file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

    }
}
