create database basketball_league;
use basketball_league;
CREATE TABLE Team(
	name VARCHAR(50) UNIQUE NOT NULL,
    street_name VARCHAR(50),
    city_name VARCHAR(50),
    win_number INT DEFAULT 0 CHECK (win_number >0),
    draw_number INT DEFAULT 0 CHECK (draw_number >0),
    loss_number INT DEFAULT 0 CHECK (loss_number >0),
    c_family_name VARCHAR(50) NOT NULL,
	c_given_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (name)
);

#Team
Insert Into team values('Hawks', '1 Philips Dr NW.', 'Atlanta', 4, 2, 0, 'Budenholzer', 'Mike');
Insert Into team values('Dodgers', 'Academy Road', 'Los Angeles', 3, 0, 2, 'Roberts', 'Eric');
Insert Into team values('Warriors', 'Coliseum Road', 'Oakland', 2, 1, 3, 'Cole', 'Steve');
Insert Into team values('Clippers', 'South Figueroa Street', 'Los Angeles', 1, 0, 3, 'Rivers', 'Doug');
Insert Into team values('Heat', ' 1 SE Third Ave', 'Miami', 0, 1, 2, 'Spoelstra', 'Eric');

CREATE TABLE Coach(
	c_family_name VARCHAR(50) NOT NULL,
    c_given_name VARCHAR(50) NOT NULL,
    CONSTRAINT PRIMARY KEY (c_family_name,c_given_name)
);

#Coach/Manager
Insert Into Coach values('Budenholzer', 'Mike');
Insert Into Coach values('Roberts', 'Eric');
Insert Into Coach values('Cole', 'Steve');
Insert Into Coach values('Rivers', 'Doug');
Insert Into Coach values('Spoelstra', 'Eric');

CREATE TABLE Player(
	ID VARCHAR(50) UNIQUE NOT NULL,
    p_family_name VARCHAR(50) NOT NULL,
	p_given_name VARCHAR(50) NOT NULL,
    age INT DEFAULT 18 CHECK (age >= 18 and age <= 40),
    estimated_value DOUBLE CHECK(estimated_value>0),
    played_years INT CHECK(played_years>0),
    team_name VARCHAR(50) NOT NULL,
    price DOUBLE CHECK(price>0),
    buy_year INT CHECK(buy_year>0),
	c_family_name VARCHAR(50) NOT NULL,
	c_given_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (team_name) REFERENCES Team(name)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT Player FOREIGN KEY (c_family_name,c_given_name) REFERENCES Coach(c_family_name,c_given_name)
    ON DELETE CASCADE ON UPDATE CASCADE
);

#Player
Insert Into player values('0101', 'Wade', 'Dwyane', 25, 13000000, 8, 'Hawks', 3000000, 2011, 'Budenholzer', 'Mike');
Insert Into player values('0102', 'Durant', 'Kevin', 23, 12000000, 7, 'Hawks', 2500000, 2012, 'Budenholzer', 'Mike');
Insert Into player values('0201', 'James', 'LeBron', 24, 15000000, 9, 'Dodgers', 3500000, 2010, 'Roberts', 'Eric');
Insert Into player values('0202', 'DeRozan', 'Demar', 24, 13000000, 7, 'Dodgers', 3500000, 2012, 'Roberts', 'Eric');
Insert Into player values('0301', 'Gordon', 'Aaron', 22, 12000000, 4, 'Warriors', 2000000, 2015, 'Cole', 'Steve');
Insert Into player values('0302', 'Curry', 'Stephen', 25, 20000000, 6, 'Warriors', 5000000, 2013, 'Cole', 'Steve');
Insert Into player values('0401', 'Bryant', 'Kobe', 26, 25000000, 9, 'Clippers', 6000000, 2010, 'Rivers', 'Doug');
Insert Into player values('0402', 'Allen', 'Ray', 23, 18000000, 8, 'Clippers', 5000000, 2011, 'Rivers', 'Doug');
Insert Into player values('0501', 'Curry', 'Seth', 24, 11000000, 6, 'Heat', 2000000, 2013, 'Spoelstra', 'Eric');
Insert Into player values('0502', 'Griffin', 'Blake', 21, 10000000, 3, 'Heat', 1000000, 2016, 'Spoelstra', 'Eric');

/*为Team表添加外键*/
ALTER TABLE Team
ADD CONSTRAINT Team FOREIGN KEY (c_family_name,c_given_name) REFERENCES Coach(c_family_name,c_given_name)
ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE BasketballMatch (
    away_team VARCHAR(50) NOT NULL,
    home_team VARCHAR(50) NOT NULL,
    match_date VARCHAR(50) NOT NULL,
    score VARCHAR(50) NOT NULL,
    result VARCHAR(50) NOT NULL,
    CONSTRAINT BasketballMatch PRIMARY KEY (away_team , home_team , match_date),
    FOREIGN KEY (away_team)
        REFERENCES Team (name)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (home_team)
        REFERENCES Team (name)
        ON DELETE CASCADE ON UPDATE CASCADE
);

#BasketballMatch 
#result represents whether home team wins or not
Insert Into BasketballMatch values('Heat', 'Hawks', '9 Mar 2019', '85:98', 'lose');
Insert Into BasketballMatch values('Hawks', 'Dodgers', '12 Mar 2019', '93:88', 'win');
Insert Into BasketballMatch values('Hawks', 'Warriors', '16 Mar 2019', '87:85', 'win');
Insert Into BasketballMatch values('Hawks', 'Clippers', '23 Mar 2019', '90:89', 'win');
Insert Into BasketballMatch values('Warriors', 'Hawks', '29 Mar 2019', '87:87', 'draw');
Insert Into BasketballMatch values('Heat', 'Hawks', '31 Mar 2019', '68:68', 'draw');
Insert Into BasketballMatch values('Dodgers', 'Heat', '2 April 2019', '83:81', 'win');
Insert Into BasketballMatch values('Dodgers', 'Warriors', '5 April 2019', '101:97', 'win');
Insert Into BasketballMatch values('Clippers', 'Dodgers', '8 April 2019', '89:92', 'lose');
Insert Into BasketballMatch values('Warriors', 'Dodgers', '12 April 2019', '99:98', 'win');
Insert Into BasketballMatch values('Warriors', 'Clippers', '18 April 2019', '97:95', 'win');
Insert Into BasketballMatch values('Clippers', 'Warriors', '23 April 2019', '94:90', 'win');