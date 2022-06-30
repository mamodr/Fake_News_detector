import keras
from Vectorization import embedding_dataset
import pickle
from pickle import load
import tensorflow

import matplotlib.pyplot as plt
import scikitplot.metrics as skplt
from sklearn import metrics

#xtr,xte,ytr,yte = embedding_dataset()

xtr,xte,ytr,yte = embedding_dataset()

model = tensorflow.keras.Sequential()
model.add(embedding_dataset())
model.add(tensorflow.keras.LSTM(128,input_shape=(embedding_dataset())),activation='relu',return_sequences=True)
model.add(tensorflow.keras.Dropout(0.2))
model.add(tensorflow.keras.LSTM(128,activation='relu'))
model.add(tensorflow.keras.Dropout(0.2))
model.add(tensorflow.keras.Dense(32,activation='relu'))
model.add(tensorflow.keras.Dropout(0.2))
model.add(tensorflow.keras.Dense(4,activation='softmax'))
model.compile(loss='sparse_categorical_crossentropy',optimizer='adam',metrics=['accuracy'])
print(model.summary())

model.fit(xtr,ytr,validation_data=(xte,yte),epochs=120,batch_size=64)

pickle.dump(model, open("LSTM_model.pkl", 'wb'))

#results = model.evaluate(xte,yte)

y_pred = model.predict(xte)

print("Accuracy:", format(metrics.accuracy_score(yte, y_pred)*100, '.2f'))
skplt.plot_confusion_matrix(yte,y_pred)
plt.show()