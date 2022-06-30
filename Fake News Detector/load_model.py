import numpy as np
import sklearn
from collections import Counter

import pre_processing as pr
import keras
from keras.preprocessing import text,sequence
from keras.models import Sequential
from keras.layers import Dense,Embedding,LSTM,Dropout
from keras.models import load_model


txt="Clinton Campaign Demands FBI Affirm Trump's Russia Ties 2016 election campaign winding down, the Clinton campaign is ratcheting up demands for the FBI to publicly confirm the campaignâ€™s allegations that Republican nominee Donald Trump is secretly in league with Russia. Sen. Harry Reid (D â€“ NV) went so far as to claim the FBI has secret â€œexplosiveâ€ evidence of coordination between the Trump campaign and the Russian government that it is withholding.FBI officials familiar with their investigations into the allegations, which the Clinton campaign started publicizing around the Democratic National Convention, say theyâ€™ve turned up nothing to connect Trump and Russia , leading FBI Director James Comey to decide against making any statements to that effect. The Clinton campaign has been making the allegations so long that they have taken to claiming â€œeveryone knowsâ€ that they are true, and appears unsettled by the FBIâ€™s refusal to sign off on the claims simply because they havenâ€™t been able to find real evidence corroborating the story. The Trump campaign has repeatedly denied ties to Russia, but that didnâ€™t stop Clinton from calling Trump a â€œpuppetâ€ of Russian President Vladimir Putin during the final presidential debate. The calls have grown since Fridayâ€™s FBI report to Congress about further Clinton emails being sought. With Clintonâ€™s main campaign scandal growing in the waning weeks of the deal, some in her campaign have suggested that affirming Trump as secretly in league with the Russians would only be fair. Absent any evidence, however, it appears that wonâ€™t be happening."


new_test=pr.clean_text(txt)
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

max_review_length = 500
print(x_train)

X_train = sequence.pad_sequences(x_train, maxlen=500)


print(X_train)
model = load_model('model.h5')
print(model.summary())

y_pred = model.predict(X_train)

print(y_pred)
print(y_pred< 0.5)
print("PREDICATION => ", str(y_pred[0][0]))
