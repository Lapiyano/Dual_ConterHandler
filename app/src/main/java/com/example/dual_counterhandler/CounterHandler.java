package com.example.dual_counterhandler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


/**
 * Credit to NomanR https://gist.github.com/nomanr
 */
public class CounterHandler {



    final Handler handler = new Handler();
    private final View incrementalView;
    private final View decrementalView;
    private final View incrementalView2;
    private final View decrementalView2;
    private double minRange = -1;
    private double maxRange = -1;
    private double startNumber=0;
    private double counterStep = 1;
    private double counterStep2 =1;
    private int counterDelay = 50; //millis
    private boolean isCycle = false;
    private boolean autoIncrement = false;
    private boolean autoDecrement = false;
    private boolean autoIncrement2 = false;
    private boolean autoDecrement2 = false;
    private final CounterListener listener;
    private final Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            if (autoIncrement) {
                increment();
                handler.postDelayed(this, counterDelay);
            } if (autoDecrement) {
                decrement();
                handler.postDelayed(this, counterDelay);
            }

            if (autoIncrement2) {
                increment2();
                handler.postDelayed(this, counterDelay);
            } if (autoDecrement2) {
                decrement2();
                handler.postDelayed(this, counterDelay);
            }

        }
    };



    private CounterHandler(Builder builder) {

        incrementalView = builder.incrementalView;
        decrementalView = builder.decrementalView;
        incrementalView2 = builder.incrementalView2;
        decrementalView2 = builder.decrementalView2;
        minRange = builder.minRange;
        maxRange = builder.maxRange;
        startNumber =builder.startNumber;
        counterStep = builder.counterStep;
        counterStep2 =builder.counterStep2;
        counterDelay = builder.counterDelay;
        isCycle = builder.isCycle;
        listener = builder.listener;
        initDecrementalView();
        initIncrementalView();
        initDecrementalView2();
        initIncrementalView2();


        if (listener != null) {
            listener.onIncrement(incrementalView, startNumber);
            listener.onDecrement(decrementalView, startNumber);
            listener.onIncrement2(incrementalView2, startNumber);
            listener.onDecrement2(decrementalView2, startNumber);
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    private void initIncrementalView() {


        incrementalView.setOnClickListener(v -> {
            increment();
            autoDecrement=false;
            autoDecrement2 = false;
            autoIncrement2=false;
        });
        incrementalView.setOnLongClickListener(v -> {
            autoIncrement = true;
            handler.postDelayed(counterRunnable, counterDelay);
            autoDecrement=false;
            autoDecrement2 = false;
            autoIncrement2=false;
            return false;
        });
        incrementalView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement  || event.getAction() == MotionEvent.ACTION_DOWN && autoIncrement ) {
                autoIncrement = false;
                autoDecrement=false;

            }
            return false;
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initIncrementalView2() {


        incrementalView2.setOnClickListener(v -> {
            increment2();
            autoDecrement=false;
            autoDecrement2 = false;
            autoIncrement=false;
        });
        incrementalView2.setOnLongClickListener(v -> {
            autoIncrement2 = true;
            handler.postDelayed(counterRunnable, counterDelay);
            autoDecrement=false;
            autoDecrement2 = false;
            autoIncrement=false;
            return false;
        });
        incrementalView2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement2) {
                autoIncrement2 = false;
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initDecrementalView() {
        decrementalView.setOnClickListener(v -> {
            decrement();
            autoDecrement2 = false;
            autoIncrement=false;
            autoIncrement2=false;
        });
        decrementalView.setOnLongClickListener(v -> {
            autoDecrement = true;
            handler.postDelayed(counterRunnable, counterDelay);

            autoDecrement2 = false;
            autoIncrement=false;
            autoIncrement2=false;
            return false;
        });
        decrementalView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement) {
                autoDecrement = false;
                autoIncrement = false;
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initDecrementalView2() {
        decrementalView2.setOnClickListener(v -> {
            decrement2();
            autoDecrement = false;
            autoIncrement=false;
            autoIncrement2=false;
        });
        decrementalView2.setOnLongClickListener(v -> {
            autoDecrement2 = true;
            handler.postDelayed(counterRunnable, counterDelay);
            autoDecrement = false;
            autoIncrement=false;
            autoIncrement2=false;
            return false;
        });
        decrementalView2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement2) {
                autoDecrement2 = false;
                autoIncrement = false;
            }
            return false;
        });
    }

    private void increment() {
        double number = startNumber;

        if (maxRange != -1) {
            if (number + counterStep <= maxRange) {
                number += counterStep;
            } else if (isCycle) {
                number = minRange == -1 ? 0 : minRange;
            }
        } else {
            number += counterStep;
        }

        if (number != startNumber && listener != null) {
            startNumber = number;
            listener.onIncrement(incrementalView, startNumber);
        }

    }

    private void increment2() {
        double number = startNumber;

        if (maxRange != -1) {
            if (number + counterStep2 <= maxRange) {
                number += counterStep2;
            } else if (isCycle) {
                number = minRange == -1 ? 0 : minRange;
            }
        } else {
            number += counterStep2;
        }

        if (number != startNumber && listener != null) {
            startNumber = number;
            listener.onIncrement2(incrementalView2, startNumber);
        }

    }

    private void decrement() {
        double number = startNumber;

        if (minRange != -1) {
            if (number - counterStep >= minRange) {
                number -= counterStep;
            } else if (isCycle) {
                number = maxRange == -1 ? 0 : maxRange;

            }
        } else {
            number -= counterStep;
        }

        if (number != startNumber && listener != null) {
            startNumber = number;
            listener.onDecrement(decrementalView, startNumber);
        }
    }

    private void decrement2() {
        double number = startNumber;

        if (minRange != -1) {
            if (number - counterStep2 >= minRange) {
                number -= counterStep2;
            } else if (isCycle) {
                number = maxRange == -1 ? 0 : maxRange;

            }
        } else {
            number -= counterStep2;
        }

        if (number != startNumber && listener != null) {
            startNumber = number;
            listener.onDecrement2(decrementalView2, startNumber);
        }
    }



    public interface CounterListener {
        void onIncrement(View view,  double number);
        void onDecrement(View view, double number);

        void onIncrement2(View view,  double number);
        void onDecrement2(View view, double number);
    }
    public static final class Builder {
        private View incrementalView;
        private View decrementalView;
        private View incrementalView2;
        private View decrementalView2;
        private double minRange=-1;
        private double maxRange=-1;
        private double startNumber=0;
        private double counterStep =1;
        public double counterStep2=0.01;
        private int counterDelay=50;
        private boolean isCycle;
        private CounterListener listener;
        public Builder() {
        }
        public Builder incrementalView(View val) {
            incrementalView = val;
            return this;
        }
        public Builder incrementalView2(View val) {
            incrementalView2 = val;
            return this;
        }
        public Builder decrementalView(View val) {
            decrementalView = val;
            return this;
        }
        public Builder decrementalView2(View val) {
            decrementalView2 = val;
            return this;
        }
        public Builder minRange(double val) {
            minRange = val;
            return this;
        }
        public Builder maxRange(double val) {
            maxRange = val;
            return this;
        }
        public Builder startNumber(double val) {
            startNumber = val;
            return this;
        }
        public Builder counterStep(double val) {
            counterStep = val;
            return this;
        }

        public Builder counterStep2(double val) {
            counterStep2 = val;
            return this;
        }
        public Builder counterDelay(int val) {
            counterDelay = val;
            return this;
        }
        public Builder isCycle(boolean val) {
            isCycle = val;
            return this;
        }
        public Builder listener(CounterListener val) {
            listener = val;
            return this;
        }
        public CounterHandler build() {
            return new CounterHandler(this);
        }


    }
}


