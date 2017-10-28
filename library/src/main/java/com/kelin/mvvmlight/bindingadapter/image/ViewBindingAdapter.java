package com.kelin.mvvmlight.bindingadapter.image;

import android.databinding.BindingAdapter;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {



    @BindingAdapter("img_src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"local_uri"})
    public static void setImageUri(ImageView imageView, String path) {
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    Picasso.with(imageView.getContext())//
                            .load(Uri.fromFile(new File(path)))//
//                        .resize(dipToPixel(width),dipToPixel(height))
                            .into(imageView);
                }else {
                    Picasso.with(imageView.getContext())//
                            .load(path)//
                            .into(imageView);
                }

            }catch (Exception e){
            }

        }
    }

    @BindingAdapter("animatedVisibility")
    public static void setVisibility(final ImageView view, final boolean playAnimation) {
        try {
            AnimationDrawable animation = (AnimationDrawable) view.getDrawable();
            if (playAnimation) {
                animation.start();
            } else {
                animation.stop();
            }
        }catch (Exception e){
            //  Logger.e(e,"显示语音");
        }
    }

//    @BindingAdapter(value = {"uri", "placeholderImageRes", "request_width", "request_height", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
//    public static void loadImage(final ImageView imageView, String uri,
//                                 @DrawableRes int placeholderImageRes,
//                                 int width, int height,
//                                 final ReplyCommand<Bitmap> onSuccessCommand,
//                                 final ReplyCommand<DataSource<CloseableReference<CloseableImage>>> onFailureCommand) {
//        imageView.setImageResource(placeholderImageRes);
//        if (!TextUtils.isEmpty(uri)) {
//            ImagePipeline imagePipeline = Fresco.getImagePipeline();
//            ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri));
//            if (width > 0 && height > 0) {
//                builder.setResizeOptions(new ResizeOptions(width, height));
//            }
//            ImageRequest request = builder.build();
//            DataSource<CloseableReference<CloseableImage>>
//                    dataSource = imagePipeline.fetchDecodedImage(request, imageView.getContext());
//            dataSource.subscribe(new BaseBitmapDataSubscriber() {
//                @Override
//                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//                    if (onFailureCommand != null) {
//                        onFailureCommand.execute(dataSource);
//                    }
//                }
//
//                @Override
//                protected void onNewResultImpl(Bitmap bitmap) {
//                    imageView.setImageBitmap(bitmap);
//                    if (onSuccessCommand != null) {
//                        onSuccessCommand.execute(bitmap);
//                    }
//                }
//            }, UiThreadImmediateExecutorService.getInstance());
//        }
//    }

}

