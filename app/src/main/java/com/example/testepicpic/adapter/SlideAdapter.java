package com.example.testepicpic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.testepicpic.R;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public int[] slidesGifs = {

            R.raw.giftela1,
            R.raw.giftelaintro2,
            R.raw.giftelaintro3,
            R.raw.giftelaintro4

    };

    public String[] slidesTitulos = {

            "Organização",
            "Inserção",
            "Gráficos",
            "Lembretes"

    };

    public String[] slidesTextos = {

            "Descubra como se organizar da melhor forma e diga adeus as planilhas.",
            "No Meritasu você pode inserir suas anotações diárias muito rapidamente.",
            "Dados monitorados são devolvidos em uma visualização de gráficos.",
            "Você pode adicionar lembretes para seus medicamentos."
    };

    @Override
    public int getCount() {
        return slidesTitulos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.avGifOrg);
        TextView textViewTitulo = view.findViewById(R.id.txtSlide);
        TextView textViewBaixo = view.findViewById(R.id.txtSlideBaixo);

        lottieAnimationView.setAnimation(slidesGifs[position]);
        textViewTitulo.setText(slidesTitulos[position]);
        textViewBaixo.setText(slidesTextos[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout) object);

    }
}
