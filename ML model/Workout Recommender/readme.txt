1. dataset_preprocessing.ipynb
	input: megaGymDataset.csv
	output: workout_dataset.csv (Untuk database)

2. encode_data.ipynb
	input: workout_dataset.csv
	output: encodings.pickle (Untuk load di runtime)

3. get_recom.ipynb
	input: encodings.pickle, id (id workout yang dipilih user)
	output: 3x id (id workout yang direkomendasikan)