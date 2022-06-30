package com.example.news_detection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyencse.URLEmbeddedData;
import com.nguyencse.URLEmbeddedView;
import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import  butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.nguyencse.URLEmbeddedView;
import com.nguyencse.URLEmbeddedData;

public class url_recyclerview_adabter extends RecyclerView.Adapter<url_recyclerview_adabter.url_viewholder> {


    Context context;
    List<url_result> urls_list;

    public url_recyclerview_adabter(Context context, List<url_result> urls_list) {
        this.context = context;
        this.urls_list = urls_list;
    }

    @NonNull
    @Override
    public url_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new url_viewholder(LayoutInflater.from(context).inflate(R.layout.url_prev,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull url_viewholder holder, int position) {


        holder.url_uev1.setVisibility(View.VISIBLE);
        holder.url_uev1.setURL(urls_list.get(position).url, new URLEmbeddedView.OnLoadURLListener() {
            @Override
            public void onLoadURLCompleted(URLEmbeddedData data) {
                holder.url_uev1.title(data.getTitle());
                holder.url_uev1.description(data.getDescription());
                holder.url_uev1.host(data.getHost());
                holder.url_uev1.thumbnail(data.getThumbnailURL());
                holder.url_uev1.favor(data.getFavorURL());
                holder.detecting_times.setVisibility(View.VISIBLE);
            }
        });
        utils.getJsoupContent(urls_list.get(position).url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result->{
                            if(result !=null) {
                                Elements metatags = result.getElementsByTag("meta");
                                for(Element element : metatags ){



                                     if(element.attr("property").equals("og:url")){
                                        holder.url_layout.setOnClickListener(v -> {
                                            String browserUrl = element.attr( "content");
                                            Intent i= new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(browserUrl));
                                            context.startActivity(i);
                                        });


                                    }

                                   // holder.progressBar.setVisibility(View.GONE);
                                    holder.url_layout.setVisibility(View.VISIBLE);
                                    holder.detecting_times.setText(String.valueOf( urls_list.get(position).incidence)+"  Users searched for this URL");

                                }
                            }
                            else {
                                Toast.makeText(context, "url error1", Toast.LENGTH_SHORT).show();
                            }
                        },error->{Toast.makeText(context, "url error2", Toast.LENGTH_SHORT).show();}
                )
        ;

    }

    @Override
    public int getItemCount() {
        return urls_list.size();
    }

    public class url_viewholder extends RecyclerView.ViewHolder{


        private Unbinder Unbinder;


        @BindView(R.id.detecting_times)
        TextView detecting_times;


        @BindView(R.id.url_layout)
        ConstraintLayout url_layout;

        @BindView(R.id.url_uev1)
        URLEmbeddedView url_uev1;

        public url_viewholder(@NonNull View itemView) {
        super(itemView);
            Unbinder =ButterKnife.bind(this,itemView);
        }



    }
}
