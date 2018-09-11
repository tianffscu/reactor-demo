package com.tianff.reactordemo.android.simulation.main.impl;

import com.tianff.reactordemo.android.simulation.main.OnClickListener;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Component
public class ReactorDemoActivity extends MainActivity implements OnClickListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactorDemoActivity.class);

    @Autowired
    private List<HttpMessageConverter<?>> httpMessageConverters;

    private List<String> picUrl = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        initPicUrl();
    }

    @Override
    public void onClick() {
        mockHttpRequest();
    }

    private void initPicUrl() {
        picUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3762951113,1294122467&fm=27&gp=0.jpg");
        picUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3762951113,1294122467&fm=27&gp=0.jpg");
        picUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3762951113,1294122467&fm=27&gp=0.jpg");
        picUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3762951113,1294122467&fm=27&gp=0.jpg");
        picUrl.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3762951113,1294122467&fm=27&gp=0.jpg");
    }

    private void mockHttpRequest() {
        Observable
                .create(emitter -> picUrl.forEach(emitter::onNext))
                .map(item -> {
                    System.out.println("Map on Thread : " + Thread.currentThread().getName());
                    RestTemplate restTemplate = new RestTemplate(httpMessageConverters);
                    byte[] imageBytes = restTemplate.getForObject((String) item, byte[].class);
                    String fileName = "Image" + System.currentTimeMillis() + ".jpg";
                    Files.write(Paths.get(fileName), imageBytes);
                    return fileName;
                })
                .subscribeOn(Schedulers.io())
                .subscribe(next -> {
                    LOGGER.info("Refreshed image: " + next);
                });
    }
}
