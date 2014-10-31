This is a simple example of recommender/evaluation Eclipse project with Mahout.
There are 3 types of reaommenders: User-Based, Item-Based, SlopeOne.(Note that Slope One Recommender is no longer supported by Mahout 0.9. Mahout 0.7 worked for me.)
There are two types of performance Evaluator: RMS and AverageAbsoluteDifference
There are also multiple different similarity measure, in code you can find both LogLikelihood and PearsonCorrelation.

