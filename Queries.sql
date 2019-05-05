/*
1.	展现所有team的win lose draw并计算point
*/
select team_name as Name, (win_number+draw_number+loss_number) as Played, win_number as Win,draw_number as Draw, loss_number as Loss, (win_number*2+draw_number) as Points
from Team;

/*
2.	在1的基础上改变point计算方法展现明年的point
*/
select team_name as Name, (win_number+draw_number+loss_number) as Played, win_number as Win,draw_number as Draw, loss_number as Loss, (win_number*2+draw_number) as Points, (win_number*3+draw_number*2-loss_number*1) as 'Next Year\'s Points'
from Team;
/*
3.	根据队伍、时间查询match或及match的属性
*/
select * from basketballmatch
where away_team = 'Clippers' and home_team = 'Warriors' and match_date = '23 April 2019';
/*
4.	根据date查询当天所有比赛
*/
select * from basketballmatch
where match_date = '23 April 2019';
/*
5.	根据队伍名列出两个队伍所有的matches
*/
select * from basketballmatch
where (away_team = 'Clippers' and home_team = 'Warriors') or (away_team = 'Warriors' and home_team = 'Clippers');
/*
6.	根据player的Id查询其所属队伍/coach/或team的其他属性
*/
select * from player
where ID = '0202';
/*
7.	根据player的name查询所有同family_name的player对应的队伍及属性
*/
select p1.ID as ID_1,p1.p_family_name as p_family_name_1,p1.p_given_name as p_given_name_1,p1.team_name as team_1 ,p2.ID as ID_2,p2.p_family_name as family_name_2,p2.p_given_name as given_name_2 ,p2.team_name as team_2 from player p1,player p2
where p1.p_family_name = p2.p_family_name and p1.ID<p2.ID;
/*
8.	根据team name查其所有players/coach/自身属性
*/
select ID,p_family_name,p_given_name from player
where team_name = 'Clippers';
/*
9.	根据coach name查所属team或其所购买players及buy的year或price
*/
select ID,p_family_name,p_given_name,price, buy_year from player
where c_family_name = 'Cole' and c_given_name = 'Steve';
/*
10.	查询所有team对应的coach/manager（一对一）或其自身属性
*/
select c_family_name as 'family name' , c_given_name as 'given name' from team
where team_name = 'Clippers'