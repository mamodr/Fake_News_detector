from flask import Flask,request,jsonify
import numpy as np
import pickle

import numpy as np
import sklearn
from collections import Counter

import pre_processing as pr
import keras
from keras.preprocessing import text,sequence
from keras.models import Sequential
from keras.layers import Dense,Embedding,LSTM,Dropout
from keras.models import load_model



app = Flask(__name__)
@app.route('/')
def index():
    return "Hello world"
@app.route('/predict/<txt>',methods=['GET', 'POST'])
def predict(txt):
    new_test = pr.clean_text(txt)
    cnt = Counter()
    x_train = []
    x_train.append(new_test.split())

    for word in x_train[-1]:
        cnt[word] += 1

    top_words = 5000

    most_common = cnt.most_common(top_words + 1)

    word_bank = {}
    id_num = 1
    for word, freq in most_common:
        word_bank[word] = id_num
        id_num += 1

    for news in x_train:
        i = 0
        while i < len(news):
            if news[i] in word_bank:
                news[i] = word_bank[news[i]]
                i += 1
            else:
                del news[i]

    i = 0
    while i < len(x_train):
        if len(x_train[i]) > 10:
            i += 1
        else:
            del x_train[i]

    X_train = sequence.pad_sequences(x_train, maxlen=500)

    model = load_model('model.h5')

    result = model.predict(X_train)

    return str(result<0.5)
if __name__ == '__main__':
    app.run(host="0.0.0.0")