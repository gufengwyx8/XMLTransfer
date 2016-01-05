
CREATE TABLE Author (
   id VARCHAR(250) NOT NULL ,
   text text
);

CREATE TABLE Paper(
	id VARCHAR(250) NOT NULL  ,
   text text
);


CREATE TABLE Year(
	id VARCHAR(250) NOT NULL  ,
   text text
);

CREATE TABLE Conference(
	id VARCHAR(250) NOT NULL  ,
   text text
);

CREATE TABLE Author_Paper(
	AP_A VARCHAR(250),
	AP_P VARCHAR(250)
);

CREATE TABLE Paper_Paper(
	PP_P1 VARCHAR(250),
	PP_P2 VARCHAR(250)
);

CREATE TABLE Paper_Year(
	PY_P VARCHAR(250),
	PY_Y VARCHAR(250)
);

CREATE TABLE Year_Conference(
	YC_Y VARCHAR(250),
	YC_C VARCHAR(250)
);