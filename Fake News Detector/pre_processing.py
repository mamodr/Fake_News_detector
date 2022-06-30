import contractions
import re
import string
import read_dataset as r_d
import contractions
from nltk.corpus import stopwords


stopwords=set(stopwords.words('english'))


def clean_text(text):
    '''cleaning_text'''
    # remove_extraspace
    text = re.sub(' +', ' ', text)
    # Remove_urls
    text = re.sub(r"http\S+", "", text)
    # remove_contractions
    text=contractions.fix(text)

    # remove_punctuations
    text= text.translate(str.maketrans('', '', string.punctuation))

    # Remove line breaks
    text = re.sub('\n', '', text)

    #Remove_emails
    text = re.sub(r'\S+@\S+\.\S+', "", text)

    #Remove_stop words
    splited_text = text.split()
    temp=[]
    for word in splited_text:
        if word not in stopwords:
            temp.append(word)
    text = ' '.join(temp)
    # to_lower_case
    text = text.lower()
    # Remove numbers
    text = re.sub(r'[0-9]', "", text)
    return text

