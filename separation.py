def separation():
	with open('TestCsvData.csv') as f:
		lines = f.readlines()
	ls = []
	for l in lines:
		ele = l.strip('\n').split(',')
		ls.append(ele)

	ls.sort(key=lambda x:x[1])

	january = []
	february = []
	march = []
	april = []
	may = []
	june = []
	july = []
	august = []
	september = []
	october = []
	november = []
	december = []
	complete = []

	for l in ls:
		ele = l
		## order is slno,date , num_of_days, int_rate, principal, paid, percent_paid
		start_date = ele[1].split('-')
		complete.append([ele[0],ele[4]])
		if(start_date[1]=='01'):
			january.append([ele[0],ele[4]])
		elif(start_date[1]=='02'):
			february.append([ele[0],ele[4]])
		elif(start_date[1]=='03'):
			march.append([ele[0],ele[4]])
		elif(start_date[1]=='04'):
			april.append([ele[0],ele[4]])
		elif(start_date[1]=='05'):
			may.append([ele[0],ele[4]])
		elif(start_date[1]=='06'):
			june.append([ele[0],ele[4]])
		elif(start_date[1]=='07'):
			july.append([ele[0],ele[4]])
		elif(start_date[1]=='08'):
			august.append([ele[0],ele[4]])
		elif(start_date[1]=='09'):
			september.append([ele[0],ele[4]])
		elif(start_date[1]=='10'):
			october.append([ele[0],ele[4]])
		elif(start_date[1]=='11'):
			november.append([ele[0],ele[4]])
		elif(start_date[1]=='12'):
			december.append([ele[0],ele[4]])
		else:
			print('invalid Month!!')

	return [complete,january,february,march,april,may,june,july,august,september,october,november,december]

