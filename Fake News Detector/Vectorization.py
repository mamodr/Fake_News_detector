import pandas as pd
from gensim.models import *
import numpy as np
from sklearn.utils import shuffle
from gensim.models.doc2vec import Doc2Vec, TaggedDocument
from nltk.tokenize import word_tokenize
from sklearn.model_selection import train_test_split

import read_dataset
from pre_processing import clean_text
import nltk
#nltk.download('punkt')




def embedding_dataset():
    df = read_dataset.read_data()
    df["text"] = df["text"].apply(clean_text)
    df = df.sample(frac=1)

    Case=df['label'].values

    doc = []
    for item in df['text']:
            doc.append(str(item))

    tokenized_doc = []
    for d in doc:
        tokenized_doc.append(word_tokenize(d))

    tagged_data = [TaggedDocument(d, [i]) for i, d in enumerate(tokenized_doc)]

    vector_size=100

    model = Doc2Vec(tagged_data, vector_size= vector_size, window=2, min_count=1, workers=4, epochs=100)
    model.save("doc2vec.model")

    data_length=len(model.dv)
    list_data = []
    for index in range(0, data_length):
        list_data.append(model.dv[index])

    text_train_arrays,text_test_arrays,train_labels,test_labels= train_test_split(list_data,Case,test_size=0.1, random_state=100)
    return text_train_arrays, text_test_arrays, train_labels, test_labels


