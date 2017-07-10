import numpy as np
import pandas as pd
from separation import separation
from sklearn import svm

[complete,january,february,march,april,may,june,july,august,september,october,november,december] = separation()

x_train = []
x_test = []
y_train = []
y_test = []
## For jan
for i in range(1,len(january)-20):
	data = january[i]
	x_train.append(data[0].replace('-',''))
	y_train.append(data[1])

for i in range(len(january)-20,len(january)):
	data = january[i]
	x_test.append(data[0].replace('-',''))
	y_test.append(data[1])

x = np.asarray(x_train).astype(np.float)
y = np.asarray(y_train).astype(np.float)
# xt = np.reshape(x_test,(-1,1)).astype(np.float)
# yt = np.reshape(y_test,(-1,1)).astype(np.float)

#clf = svm.SVR(kernel='rbf', C=1, gamma=0.1)
#clf.fit(x,y)
#pred = clf.predict(xt)
#print(clf.score(xt,yt))
#print(clf.score(x,y))

import matplotlib.pyplot as plt
plt.scatter(x,y,color='orange',label='data')
lw = 2
## plt.plot(x, clf.predict(x), color='navy', lw=lw, label='RBF model')
## plt.plot(x,clf.predict(x),color='red',linewidth=2)
plt.xlabel("date")
plt.ylabel("principal")
plt.legend()
plt.show()
