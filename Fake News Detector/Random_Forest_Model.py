from sklearn.ensemble import RandomForestClassifier
from Vectorization import *
from sklearn import metrics
import scikitplot.metrics as skplt
from sklearn import metrics
import matplotlib.pyplot as plt



xtr,xte,ytr,yte = embedding_dataset()


clf=RandomForestClassifier(n_estimators=100)
clf.fit(xtr,ytr)

y_pred=clf.predict(xte)



print("Accuracy:", format(metrics.accuracy_score(yte, y_pred)*100, '.2f'))
skplt.plot_confusion_matrix(yte,y_pred)
plt.show()

