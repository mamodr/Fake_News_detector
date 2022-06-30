import pickle
from pickle import load
from keras.layers import embeddings
import read_dataset

from Vectorization import embedding_dataset
from sklearn.metrics import plot_confusion_matrix, recall_score, precision_score
import matplotlib.pyplot as plt
from sklearn import svm
from sklearn import metrics
import scikitplot.metrics as skplt
import  pickle
filename = 'SVM_model.sav'





X_train, X_test, y_train, y_test = embedding_dataset()
# Create a svm Classifier
clf = svm.SVC(kernel='linear')  # Linear Kernel
clf.fit(X_train, y_train)
# save the model to disk

pickle.dump(clf, open(filename, 'wb'))

# Predict the response for test dataset
y_pred = clf.predict(X_test)
# Model Accuracy: how often is the classifier correct?
print("Accuracy:", metrics.accuracy_score(y_test, y_pred))
precision = precision_score(y_test, y_pred)
# Model Precision
print('Precision: %f' % precision)
recall = recall_score(y_test, y_pred)
print('Recall: %f' % recall)


# Model Precision
skplt.plot_confusion_matrix(y_test,y_pred)

plt.show()

