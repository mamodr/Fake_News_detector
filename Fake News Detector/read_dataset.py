import pandas as pd
from numpy import dtype


def read_data():
    df=pd.read_csv('data.csv',nrows=100)
    return df


