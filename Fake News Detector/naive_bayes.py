from Vectorization import embedding_dataset
from sklearn.naive_bayes import GaussianNB
import numpy as np
import matplotlib.pyplot as plt
import scikitplot.metrics as skplt
from sklearn import metrics






xtr,xte,ytr,yte = embedding_dataset()


gnb = GaussianNB()
gnb.fit(xtr,ytr)
y_pred = gnb.predict(xte)



print("Accuracy:", format(metrics.accuracy_score(yte, y_pred)*100, '.2f'))
skplt.plot_confusion_matrix(yte,y_pred)
plt.show()