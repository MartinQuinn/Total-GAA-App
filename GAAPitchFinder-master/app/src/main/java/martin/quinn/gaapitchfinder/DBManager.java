package martin.quinn.gaapitchfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE = "gaa.db";

    private static final String TABLE_COUNTIES =
            "Counties";
    private static final String KEY_COUNTY_ID =
            "_id";
    private static final String KEY_COUNTY_NAME =
            "county_name";

    private static final String TABLE_CLUBS =
            "Clubs";
    private static final String KEY_CLUB_ID =
            "_id";
    private static final String KEY_CLUB_NAME =
            "club_name";
    private static final String KEY_COLOURS =
            "colours";
    private static final String KEY_CLUB_DESCRIPTION =
            "club_description";
    private static final String KEY_CLUB_LOCATION =
            "location";
    private static final String KEY_CLUB_WEBSITE =
            "website";
    private static final String FOREIGN_KEY_ID =
            "county_id_fk";

    private static final String CREATE_COUNTY_TABLE =
            "CREATE TABLE Counties " +
                    "(" +
                    "_id INTEGER PRIMARY KEY autoincrement, " +
                    "county_name TEXT);";

    private static final String CREATE_CLUB_TABLE =
            "CREATE TABLE Clubs " +
                    "(" +
                    "_id INTEGER PRIMARY KEY autoincrement, " +
                    "county_id_fk INTEGER, " +
                    "club_name TEXT, " +
                    "colours TEXT, " +
                    "club_description TEXT, " +
                    "location TEXT, " +
                    "website TEXT," +
                    "FOREIGN KEY(county_id_fk) REFERENCES Counties(_id));"
            ;

    private static final String INITIAL_COUNTY_INSERTS =
            "INSERT INTO " +
                    "Counties (County_name) " +
                    "VALUES" +
                    "('Antrim')," +
                    "('Armagh')," +
                    "('Carlow')," +
                    "('Cavan')," +
                    "('Clare')," +
                    "('Cork')," +
                    "('Derry')," +
                    "('Donegal')," +
                    "('Down')," +
                    "('Dublin')," +
                    "('Fermanagh')," +
                    "('Galway')," +
                    "('Kerry')," +
                    "('Kildare')," +
                    "('Kilkenny')," +
                    "('Laois')," +
                    "('Leitrim')," +
                    "('Limerick')," +
                    "('Longford')," +
                    "('Louth')," +
                    "('Mayo')," +
                    "('Meath')," +
                    "('Monaghan')," +
                    "('Offaly')," +
                    "('Roscommon')," +
                    "('Sligo')," +
                    "('Tipperary')," +
                    "('Tyrone')," +
                    "('Waterford')," +
                    "('Westmeath')," +
                    "('Wexford')," +
                    "('Wicklow');"
            ;


    private static final String INITIAL_ANTRIM_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('1','All Saints',' ',' ','https://maps.google.co.uk/maps?daddr=54.867705,-6.226931&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Antrim Centre of Excellence',' ',' ','https://maps.google.co.uk/maps?daddr=54.736927,-6.230541&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Ardoyne Kickhams',' ',' ','https://maps.google.co.uk/maps?daddr=54.685185,-5.997307&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Ballymoney',' ',' ','https://maps.google.co.uk/maps?daddr=55.067433,-6.520901&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Boucher Road Pitches',' ',' ','https://maps.google.co.uk/maps?daddr=54.576092,-5.970855&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Cardinal ODonnells',' ',' ','https://maps.google.co.uk/maps?daddr=54.590510,-5.972614&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Carey Faughs Ballyvoy',' ',' ','https://maps.google.co.uk/maps?daddr=55.197996,-6.193843&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Casement Park (County Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=54.573044,-5.984244&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Cherryvale Pitches',' ',' ','https://maps.google.co.uk/maps?daddr=54.575146,-5.912061&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Cliftonville',' ',' ','https://maps.google.co.uk/maps?daddr=54.617040,-5.946876&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Clooney Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.823777,-6.387702&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Con Magees Glenravel',' ',' ','https://maps.google.co.uk/maps?daddr=54.984611,-6.186435&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Creggan Kickhams',' ',' ','https://maps.google.co.uk/maps?daddr=54.720713,-6.361288&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Crumlin Leisure Centre',' ',' ','https://maps.google.co.uk/maps?daddr=Crumlin+Leisure+Centre%2C+Main+Street%2C+Crumlin%2C+United+Kingdom&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Cuchullains Dunloy',' ',' ','https://maps.google.co.uk/maps?daddr=55.007653,-6.411923&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Eire Og',' ',' ','https://maps.google.co.uk/maps?daddr=54.568234,-5.985183&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Erins Own Cargin',' ',' ','https://maps.google.co.uk/maps?daddr=54.748362,-6.460358&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Falls Park Pitches',' ',' ','https://maps.google.co.uk/maps?daddr=54.586935,-5.984609&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Fr Healy Park',' ',' ','https://maps.google.co.uk/maps?daddr=55.057188,-6.319113&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Glen Rovers Armoy',' ',' ','https://maps.google.co.uk/maps?daddr=55.137119,-6.295971&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Gort na Mona',' ',' ','https://maps.google.co.uk/maps?daddr=54.589474,-5.995011&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Henry Joy McCrackens',' ',' ','https://maps.google.co.uk/maps?daddr=54.587536,-5.984270&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','John Mitchels',' ',' ','https://maps.google.co.uk/maps?daddr=54.566456,-6.030052&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Lamh Dearg',' ',' ','https://maps.google.co.uk/maps?daddr=54.588092,-6.037567&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Latharna Og Larne',' ',' ','https://maps.google.co.uk/maps?daddr=54.876829,-5.859404&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Loughgiel Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=55.060819,-6.309714&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','McQuillans Ballycastle',' ',' ','https://maps.google.co.uk/maps?daddr=55.204011,-6.273032&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Michael Davitts',' ',' ','https://maps.google.co.uk/maps?daddr=54.575221,-5.970876&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Michael Dwyers',' ',' ','https://maps.google.co.uk/maps?daddr=54.587654,-5.983756&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Oisin Glenariff',' ',' ','https://maps.google.co.uk/maps?daddr=55.054653,-6.050790&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','ODonovan Rossa',' ',' ','https://maps.google.co.uk/maps?daddr=54.574901,-6.005858&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Patrick Pearses',' ',' ','https://maps.google.co.uk/maps?daddr=54.683653,-5.996438&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Patrick Sarsfields',' ',' ','https://maps.google.co.uk/maps?daddr=54.573455,-6.008449&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Queens University Malone Playing Fields',' ',' ','https://maps.google.co.uk/maps?daddr=54.556721,-5.961775&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Rathlin Island (Temporary Pitch - The Big Field)',' ',' ','https://maps.google.co.uk/maps?daddr=55.292381,-6.190262&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Robert Emmets Cushendun',' ',' ','https://maps.google.co.uk/maps?daddr=55.129949,-6.043081&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Roger Casements Portglenone',' ',' ','https://maps.google.co.uk/maps?daddr=54.882063,-6.483361&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Ruairi Og Cushendall',' ',' ','https://maps.google.co.uk/maps?daddr=55.077471,-6.057844&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Sean McDermotts',' ',' ','https://maps.google.co.uk/maps?daddr=54.575208,-5.969610&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Shaftesbury Community & Recreation Centre (3G Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=54.588001,-5.920303&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Shane ONeills Glenarm',' ',' ','https://maps.google.co.uk/maps?daddr=54.928184,-5.950845&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Agnes',' ',' ','https://maps.google.co.uk/maps?daddr=54.568218,-5.986079&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Brigids Cloughmills',' ',' ','https://maps.google.co.uk/maps?daddr=55.011858,-6.328865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Brigids GAC Belfast (Harlequins)',' ',' ','https://maps.google.co.uk/maps?daddr=54.562645,-5.941737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Brigids GAC Belfast (Musgrave Park)',' ',' ','https://maps.google.co.uk/maps?daddr=54.571434,-5.975865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Comgalls Antrim',' ',' ','https://maps.google.co.uk/maps?daddr=54.721268,-6.219549&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Endas',' ',' ','https://maps.google.co.uk/maps?daddr=54.662256,-5.969841&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Ergnats Moneyglass',' ',' ','https://maps.google.co.uk/maps?daddr=54.779380,-6.422496&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Galls',' ',' ','https://maps.google.co.uk/maps?daddr=54.584703,-5.970082&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St James Aldergrove',' ',' ','https://maps.google.co.uk/maps?daddr=54.616716,-6.223916&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Johns',' ',' ','https://maps.google.co.uk/maps?daddr=54.591834,-5.977668&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Josephs Glenavy',' ',' ','https://maps.google.co.uk/maps?daddr=54.579773,-6.224045&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Malachys',' ',' ','https://maps.google.co.uk/maps?daddr=54.575376,-5.910516&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Malachys College',' ',' ','https://maps.google.co.uk/maps?daddr=36+Antrim+Road%2C+Belfast%2C+United+Kingdom&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Marys Aghagallon',' ',' ','https://maps.google.co.uk/maps?daddr=54.500214,-6.272730&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Marys Ahoghill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.824290,-6.387895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Marys Grammar School  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.583329,-6.003900&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Marys Rasharkin',' ',' ','https://maps.google.co.uk/maps?daddr=54.941873,-6.484943&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Patricks Lisburn',' ',' ','https://maps.google.co.uk/maps?daddr=54.527570,-6.049358&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Pauls',' ',' ','https://maps.google.co.uk/maps?daddr=54.575610,-6.007204&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','St Teresas',' ',' ','https://maps.google.co.uk/maps?daddr=54.578697,-6.007397&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Tir na nOg Randalstown',' ',' ','https://maps.google.co.uk/maps?daddr=54.749588,-6.286100&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','Twinbrook Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.553406,-6.019317&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','UUJ  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.689846,-5.885732&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('1','YMCA Pitches',' ',' ','https://maps.google.co.uk/maps?daddr=54.565949,-5.934781&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_ARMAGH_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('2','An Port Mor GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.414301,-6.703468&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Annaghmore Pearses GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.465860,-6.563382&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Armagh Cuchullains Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.349778,-6.660225&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Armagh Harps GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.359338,-6.661609&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Athletic Grounds (Armagh County Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=54.343812,-6.661824&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Ballyhagen Davitts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.406509,-6.573797&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Ballymacnab Round Towers',' ',' ','https://maps.google.co.uk/maps?daddr=54.289936,-6.633103&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Belleek Laurence OTooles GFC ',' ',' ','https://maps.google.co.uk/maps?daddr=54.172527,-6.488746&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Camlough Craobh Rua Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.191721,-6.392337&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Carrickcruppin St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.182656,-6.396865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Clady Sean South  ',' ',' ','https://maps.google.co.uk/maps?daddr=54.251916,-6.565903&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Clan na Gael  Lurgan',' ',' ','https://maps.google.co.uk/maps?daddr=54.462917,-6.348757&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Clann Eireann GFC Lurgan',' ',' ','https://maps.google.co.uk/maps?daddr=54.475331,-6.329820&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Clonmore Robert Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.478691,-6.642416&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Collegeland ORahillys GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.440755,-6.678776&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Crossmaglen Rangers GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.075046,-6.608534&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Cullaville Blues GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.059067,-6.638939&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Dorsey Emmets GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.142498,-6.551489&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Dromintee St Patricks GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.097610,-6.398254&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Éire Óg G.A.C Craigavon',' ',' ','https://maps.google.co.uk/maps?daddr=54.454385,-6.362082&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Grange St Colmcilles GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.392063,-6.663039&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Keady Lamh Dearg Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.256300,-6.701730&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Keady Michael Dwyers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.256231,-6.701488&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Lissummon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.252026,-6.418129&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Madden Raparees GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.317149,-6.714084&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Maghery Sean McDermotts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.513826,-6.574974&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Mullabrack (ODonovan Rossa) GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.302959,-6.536694&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Mullaghbawn Cúchullains GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.109496,-6.487169&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Na Fianna  Middletown Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.296680,-6.846778&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Owen Roes GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.296811,-6.846757&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Peadar Ó Doirnín  Forkhill',' ',' ','https://maps.google.co.uk/maps?daddr=54.084752,-6.456785&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Pearse Og Armagh GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.351992,-6.677563&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Phelim Bradys Darkley GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.224390,-6.682187&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Redmond OHanlons Ponyntzpass GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.288032,-6.375664&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Sarsfields Derrytrasna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.503063,-6.460304&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Sean Treacys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.476852,-6.330668&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Shane ONeills Camlough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.180276,-6.405898&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Silverbridge Harps GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.096534,-6.537992&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Killians GFC Whitecross',' ',' ','https://maps.google.co.uk/maps?daddr=54.221270,-6.484509&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Malachys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.432346,-6.473699&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Marys GFC Granemore',' ',' ','https://maps.google.co.uk/maps?daddr=54.251919,-6.654121&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Michaels Killean',' ',' ','https://maps.google.co.uk/maps?daddr=54.122565,-6.335397&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Michaels Newtownhamilton GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.187314,-6.573681&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Mochuas Derrynoose',' ',' ','https://maps.google.co.uk/maps?daddr=54.232233,-6.774670&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Moninnes Killeavey',' ',' ','https://maps.google.co.uk/maps?daddr=54.142428,-6.361663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Patricks Cullyhanna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.124887,-6.579400&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Pauls Lurgan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.452196,-6.348982&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','St Peters Lurgan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.467799,-6.329412&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Thomas Davis GFC Corrinshego',' ',' ','https://maps.google.co.uk/maps?daddr=54.176528,-6.363128&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Tir Na nOg Portadown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.430109,-6.461474&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Tullysarran OConnells GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.391072,-6.741679&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('2','Wolfe Tones Derrymacash GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.476814,-6.395974&t=m&layer=1&doflg=ptk&om=1',' ');"


            ;

    private static final String INITIAL_CARLOW_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('3','Asca GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.838155,-6.907101&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Ballinabranna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.786184,-6.986012&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Ballinkillen Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.648785,-6.928688&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Ballon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.734836,-6.792276&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Carlow IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.825198,-6.935710&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Carlow Town Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.848420,-6.915776&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Clonmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.854601,-6.569299&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Dr Cullen Park (Carlow County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.846826,-6.917353&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Eire Og',' ',' ','https://maps.google.co.uk/maps?daddr=52.830423,-6.917202&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Erins Own  (Primarily Hurling Club)',' ',' ','https://maps.google.co.uk/maps?daddr=52.709019,-6.948928&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Fenagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.715915,-6.846778&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Fighting Cocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.770039,-6.837053&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Grange GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.842050,-6.787319&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Kilbride GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.719083,-6.721664&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Kildavin/Clonegal',' ',' ','https://maps.google.co.uk/maps?daddr=52.683830,-6.675793&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Leighlinbridge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.741700,-6.964052&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Milford',' ',' ','https://maps.google.co.uk/maps?daddr=52.8059437%2C-6.9370226&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Mount Leinster Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=52.590105,-6.912997&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Naomh Brid Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.738432,-6.982166&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Naomh Eoin Myshall',' ',' ','https://maps.google.co.uk/maps?daddr=52.687056,-6.786160&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Old Leighlin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.737519,-7.021235&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','OHanrahans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.846113,-6.912096&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Palatine',' ',' ','https://maps.google.co.uk/maps?daddr=52.828919,-6.874153&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Rathvilly',' ',' ','https://maps.google.co.uk/maps?daddr=52.878390,-6.695588&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Setanta Ceatharlach',' ',' ','https://maps.google.co.uk/maps?daddr=52.838674,-6.907450&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','St Andrews',' ',' ','https://maps.google.co.uk/maps?daddr=52.708219,-6.947426&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','St Mullins',' ',' ','https://maps.google.co.uk/maps?daddr=52.511200,-6.922159&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','St Patricks Tullow',' ',' ','https://maps.google.co.uk/maps?daddr=52.807444,-6.744683&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('3','Tinryland GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.799755,-6.881057&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_CAVAN_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('4','Arva',' ',' ','https://maps.google.co.uk/maps?daddr=Arva%2C+Cavan%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Bailieborough Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=53.925356,-6.969634&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Ballinagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.930299,-7.412676&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Ballyhaise GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.046206,-7.315049&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Ballymachugh',' ',' ','https://maps.google.co.uk/maps?daddr=53.835126,-7.353834&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Belturbet',' ',' ','https://maps.google.co.uk/maps?daddr=54.099922,-7.438452&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Butlersbridge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.047447,-7.375522&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Castlerahan',' ',' ','https://maps.google.co.uk/maps?daddr=53.861076,-7.210282&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Cavan Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.987985,-7.363651&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Cootehill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.072509,-7.080173&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Corlough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.117961,-7.748408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Cornafean GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.949955,-7.493556&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Crosserlough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.861898,-7.315586&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Crossreagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.853707,-7.013091&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Cuchullains GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.807861,-6.938446&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Denn GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.920238,-7.264425&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Drumalee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.997339,-7.353179&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Drumgoon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.042690,-7.017980&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Drumlane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.068682,-7.478106&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Drung GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.068984,-7.226268&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Gowna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.866365,-7.550890&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Kill Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=54.054850,-7.134826&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Killashandra GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.010470,-7.526767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Killdallen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.119307,-7.585040&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Killinkere GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.884271,-7.048641&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Killygarry',' ',' ','https://maps.google.co.uk/maps?daddr=53.964531,-7.319276&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Kingscourt Stars',' ',' ','https://maps.google.co.uk/maps?daddr=53.906083,-6.799432&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Kingspan Breffni Park (Cavan County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.982106,-7.359821&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Knockbride GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.977897,-7.057976&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Lacken Celtic GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.920883,-7.425653&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Laragh United GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.983273,-7.237147&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Lavey GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.901753,-7.174952&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Maghera MacFinns GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.800448,-7.039495&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Mountnugent',' ',' ','https://maps.google.co.uk/maps?daddr=53.806049,-7.219133&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Mullahoran',' ',' ','https://maps.google.co.uk/maps?daddr=53.835366,-7.439439&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Munterconnaught GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.793199,-7.066301&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Ramor United GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.830947,-7.069820&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Redhills GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.099576,-7.321819&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Shannon Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=54.289842,-7.867198&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','Shercock GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.979926,-6.879501&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','St Aidens  Templeport',' ',' ','https://maps.google.co.uk/maps?daddr=54.121678,-7.673939&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','St Marys GFC Swanlinbar',' ',' ','https://maps.google.co.uk/maps?daddr=54.192386,-7.702532&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('4','St Patricks GFC Arvagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.921221,-7.578866&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_CLARE_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('5','Ballyea Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.786536,-9.028852&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ballyline',' ',' ','https://maps.google.co.uk/maps?daddr=52.917759,-8.916693&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ballyvaughan / Fanore',' ',' ','https://maps.google.co.uk/maps?daddr=53.113953,-9.154862&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Bodyke Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.894794,-8.551515&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Broadford Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.803566,-8.634245&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clare Abbey',' ',' ','https://maps.google.co.uk/maps?daddr=52.821567,-8.973770&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clarecastle',' ',' ','https://maps.google.co.uk/maps?daddr=52.816335,-8.971463&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clonboney',' ',' ','https://maps.google.co.uk/maps?daddr=52.856269,-9.402602&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clondegad',' ',' ','https://maps.google.co.uk/maps?daddr=52.720555,-9.063549&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clonlara Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.719853,-8.557878&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Clooney Quin',' ',' ','https://maps.google.co.uk/maps?daddr=52.836998,-8.855581&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Coolmeen',' ',' ','https://maps.google.co.uk/maps?daddr=52.666754,-9.221456&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Cooraclare',' ',' ','https://maps.google.co.uk/maps?daddr=52.696556,-9.426152&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Corofin',' ',' ','https://maps.google.co.uk/maps?daddr=52.945215,-9.067417&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Cratloe',' ',' ','https://maps.google.co.uk/maps?daddr=52.695542,-8.760835&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Crusheen',' ',' ','https://maps.google.co.uk/maps?daddr=52.936095,-8.897585&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Cusack Park (Clare County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.846385,-8.977493&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Doonbeg GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.726202,-9.546701&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Eire Og',' ',' ','https://maps.google.co.uk/maps?daddr=52.831917,-8.991242&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ennistymon',' ',' ','https://maps.google.co.uk/maps?daddr=52.935623,-9.320258&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ennistymon Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.935604,-9.320408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Feakle',' ',' ','https://maps.google.co.uk/maps?daddr=52.923348,-8.651068&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Inagh-Kilnamona',' ',' ','https://maps.google.co.uk/maps?daddr=52.877704,-9.180225&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kildysert',' ',' ','https://maps.google.co.uk/maps?daddr=52.666331,-9.110477&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilfenora',' ',' ','https://maps.google.co.uk/maps?daddr=52.994676,-9.234615&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilkee Bealatha Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.678652,-9.640321&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilkishen  (Defunct)',' ',' ','https://maps.google.co.uk/maps?daddr=52.803070,-8.755224&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Killanena Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.995012,-8.734388&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Killimer',' ',' ','https://maps.google.co.uk/maps?daddr=52.626294,-9.390725&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilmaley Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.813881,-9.095376&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilmihil',' ',' ','https://maps.google.co.uk/maps?daddr=52.722713,-9.317318&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilmurray Ibrickane',' ',' ','https://maps.google.co.uk/maps?daddr=52.814623,-9.454508&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilnamona Hurling Club (Merged with Inagh)',' ',' ','https://maps.google.co.uk/maps?daddr=52.870179,-9.061832&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilrush Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.629293,-9.472650&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Kilrush Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=52.629312,-9.472522&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Lahinch',' ',' ','https://maps.google.co.uk/maps?daddr=52.936800,-9.343218&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Liscannor GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.938992,-9.447212&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Lissycasey',' ',' ','https://maps.google.co.uk/maps?daddr=52.739429,-9.174571&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Meelick',' ',' ','https://maps.google.co.uk/maps?daddr=52.699332,-8.651787&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Michael Cusacks',' ',' ','https://maps.google.co.uk/maps?daddr=53.080309,-9.073881&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Moy',' ',' ','https://maps.google.co.uk/maps?daddr=52.906425,-9.336953&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Mullagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.798052,-9.413652&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Naomh Eoin',' ',' ','https://maps.google.co.uk/maps?daddr=52.596632,-9.778851&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Newmarket-on-Fergus Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.760392,-8.888669&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ogonnelloe Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.871610,-8.457885&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','OCallaghans Mills',' ',' ','https://maps.google.co.uk/maps?daddr=52.839795,-8.671893&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','OCurrys',' ',' ','https://maps.google.co.uk/maps?daddr=52.622103,-9.645771&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Parteen',' ',' ','https://maps.google.co.uk/maps?daddr=52.680444,-8.602901&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Ruan Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.933075,-8.987374&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Scarriff Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.912758,-8.532718&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Shannon Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=52.638220,-9.247345&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Sixmilebridge Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.744333,-8.778205&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Smith OBriens',' ',' ','https://maps.google.co.uk/maps?daddr=52.803572,-8.445836&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','St Breckans',' ',' ','https://maps.google.co.uk/maps?daddr=53.026903,-9.291773&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','St Josephs Doora-Barefield (Roslevan)',' ',' ','https://maps.google.co.uk/maps?daddr=52.857497,-8.959501&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','St Josephs Miltown Malbay',' ',' ','https://maps.google.co.uk/maps?daddr=52.852729,-9.408191&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','St Josephs Doora-Barefield (Gurteen)',' ',' ','https://maps.google.co.uk/maps?daddr=52.842530,-8.905599&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','St Senans Kilkee',' ',' ','https://maps.google.co.uk/maps?daddr=52.678568,-9.640074&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','The Banner',' ',' ','https://maps.google.co.uk/maps?daddr=52.839899,-9.020741&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Tubber Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.015345,-8.909461&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Tulla Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.861710,-8.744897&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Whitegate Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.951401,-8.374184&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('5','Wolfe Tones na Sionna',' ',' ','https://maps.google.co.uk/maps?daddr=52.704007,-8.868853&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_CORK_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('6','Abbey Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.171997,-8.475212&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Adrigole GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.684873,-9.712279&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Aghabullogue',' ',' ','https://maps.google.co.uk/maps?daddr=51.907293,-8.789148&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Aghada',' ',' ','https://maps.google.co.uk/maps?daddr=51.848027,-8.192582&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Aghinagh',' ',' ','https://maps.google.co.uk/maps?daddr=51.938326,-8.905267&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Araglin',' ',' ','https://maps.google.co.uk/maps?daddr=52.206765,-8.084586&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Argideen Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=51.638549,-8.775598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Awbeg Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=52.236204,-8.672826&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinacurra',' ',' ','https://maps.google.co.uk/maps?daddr=51.897489,-8.151094&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinascarthy',' ',' ','https://maps.google.co.uk/maps?daddr=51.667079,-8.831624&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballincollig',' ',' ','https://maps.google.co.uk/maps?daddr=51.892292,-8.589109&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballingeary',' ',' ','https://maps.google.co.uk/maps?daddr=51.850870,-9.231214&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinhassig',' ',' ','https://maps.google.co.uk/maps?daddr=51.811228,-8.529704&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinlough',' ',' ','https://maps.google.co.uk/maps?daddr=51.887511,-8.439174&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinora',' ',' ','https://maps.google.co.uk/maps?daddr=51.853766,-8.566214&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballinure',' ',' ','https://maps.google.co.uk/maps?daddr=51.889998,-8.393611&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballyclough',' ',' ','https://maps.google.co.uk/maps?daddr=52.177406,-8.728981&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballydesmond',' ',' ','https://maps.google.co.uk/maps?daddr=52.178373,-9.234234&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballygarvan',' ',' ','https://maps.google.co.uk/maps?daddr=51.824333,-8.457144&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballygiblin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.274558,-8.200210&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballyhea Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.325662,-8.663546&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballyhooley',' ',' ','https://maps.google.co.uk/maps?daddr=52.148804,-8.396612&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballymartle',' ',' ','https://maps.google.co.uk/maps?daddr=51.772826,-8.492357&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ballyphehane',' ',' ','https://maps.google.co.uk/maps?daddr=51.878925,-8.475212&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Bandon',' ',' ','https://maps.google.co.uk/maps?daddr=51.739533,-8.741598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Banteer Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.127903,-8.899291&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Bantry Blues',' ',' ','https://maps.google.co.uk/maps?daddr=51.687796,-9.447094&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Barryroe',' ',' ','https://maps.google.co.uk/maps?daddr=51.606124,-8.735032&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Belgooly',' ',' ','https://maps.google.co.uk/maps?daddr=51.733853,-8.484916&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Bere Island GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.631920,-9.819970&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Bishopstown',' ',' ','https://maps.google.co.uk/maps?daddr=51.884326,-8.520992&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Blackrock National Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.892967,-8.418853&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Blarney',' ',' ','https://maps.google.co.uk/maps?daddr=51.929131,-8.560410&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Boherbue',' ',' ','https://maps.google.co.uk/maps?daddr=52.152918,-9.079481&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Brian Dillons',' ',' ','https://maps.google.co.uk/maps?daddr=51.942274,-8.443497&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Bride Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.072272,-8.286299&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Buttevant Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.236263,-8.672783&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Canovee',' ',' ','https://maps.google.co.uk/maps?daddr=51.902971,-8.853135&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Carbery Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=51.584550,-9.024721&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Carrigaline',' ',' ','https://maps.google.co.uk/maps?daddr=51.811573,-8.379607&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Carrignavar (Carraig na bhFear)',' ',' ','https://maps.google.co.uk/maps?daddr=51.991857,-8.476017&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Carrigtwohill',' ',' ','https://maps.google.co.uk/maps?daddr=51.908418,-8.265356&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castlehaven',' ',' ','https://maps.google.co.uk/maps?daddr=51.540630,-9.218463&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castlelyons',' ',' ','https://maps.google.co.uk/maps?daddr=52.088479,-8.232150&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castlemagner',' ',' ','https://maps.google.co.uk/maps?daddr=52.165562,-8.823996&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castlemartyr',' ',' ','https://maps.google.co.uk/maps?daddr=51.906241,-8.050328&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castletownbere GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.643749,-9.925933&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Castletownroche',' ',' ','https://maps.google.co.uk/maps?daddr=52.171958,-8.475266&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Charleville Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.352309,-8.678303&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Churchtown',' ',' ','https://maps.google.co.uk/maps?daddr=52.270931,-8.735649&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Clann na nGael GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.658554,-9.260552&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Clonakilty',' ',' ','https://maps.google.co.uk/maps?daddr=51.620948,-8.885477&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Clondrohid',' ',' ','https://maps.google.co.uk/maps?daddr=51.929316,-9.020387&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Cloughduv Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.848624,-8.783537&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Cloyne',' ',' ','https://maps.google.co.uk/maps?daddr=51.860930,-8.121825&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Clyda Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.081735,-8.629181&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Cobh',' ',' ','https://maps.google.co.uk/maps?daddr=51.856039,-8.286170&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Cork IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=51.885564,-8.539681&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Courcey Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.663173,-8.594034&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Crosshaven',' ',' ','https://maps.google.co.uk/maps?daddr=51.807719,-8.280977&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Cullen',' ',' ','https://maps.google.co.uk/maps?daddr=52.114603,-9.124864&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Deel Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.340708,-8.852888&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Delaney Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.927721,-8.471822&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Diarmuid Ó Mathúna',' ',' ','https://maps.google.co.uk/maps?daddr=51.773350,-8.974028&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Dohenys Dunmanway',' ',' ','https://maps.google.co.uk/maps?daddr=51.714619,-9.111013&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Doneraile',' ',' ','https://maps.google.co.uk/maps?daddr=52.211177,-8.586588&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Donoughmore',' ',' ','https://maps.google.co.uk/maps?daddr=51.989935,-8.729496&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Douglas',' ',' ','https://maps.google.co.uk/maps?daddr=51.874722,-8.442162&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Dripsey',' ',' ','https://maps.google.co.uk/maps?daddr=51.890253,-8.631681&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Dromina Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.310670,-8.805778&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Dromtarriffe GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.097944,-8.975122&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Dungourney',' ',' ','https://maps.google.co.uk/maps?daddr=51.981831,-8.101028&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Eire Og',' ',' ','https://maps.google.co.uk/maps?daddr=51.874981,-8.669018&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Erins Own',' ',' ','https://maps.google.co.uk/maps?daddr=51.914474,-8.364297&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Fermoy',' ',' ','https://maps.google.co.uk/maps?daddr=52.143658,-8.275259&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Fr ONeills',' ',' ','https://maps.google.co.uk/maps?daddr=51.905102,-7.945604&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Freemount',' ',' ','https://maps.google.co.uk/maps?daddr=52.278819,-8.884249&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Gabriel Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=51.565767,-9.456611&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Garnish',' ',' ','https://maps.google.co.uk/maps?daddr=51.605564,-10.035142&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glanmire GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.935131,-8.398372&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glanworth',' ',' ','https://maps.google.co.uk/maps?daddr=52.195416,-8.362409&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Gleann na Laoi',' ',' ','https://maps.google.co.uk/maps?daddr=51.910384,-8.561676&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glen Rovers Hurling',' ',' ','https://maps.google.co.uk/maps?daddr=51.916033,-8.465475&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glenbower Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.939754,-8.002038&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glengarriff GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.752693,-9.531101&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glenlara',' ',' ','https://maps.google.co.uk/maps?daddr=52.206484,-9.109375&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Glenville',' ',' ','https://maps.google.co.uk/maps?daddr=52.048499,-8.423874&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Goleen',' ',' ','https://maps.google.co.uk/maps?daddr=51.498551,-9.717686&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Grange',' ',' ','https://maps.google.co.uk/maps?daddr=52.130096,-8.268532&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Grenagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.011276,-8.613442&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Gurranbrather GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.911728,-8.480136&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Harbour Rovers Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.195396,-8.362387&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Ilen Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.498778,-9.352981&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Inniscarra',' ',' ','https://maps.google.co.uk/maps?daddr=51.925928,-8.674414&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Iveleary  Inchigeelagh',' ',' ','https://maps.google.co.uk/maps?daddr=51.843103,-9.128834&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kanturk',' ',' ','https://maps.google.co.uk/maps?daddr=52.181952,-8.899237&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kiely Park',' ',' ','https://maps.google.co.uk/maps?daddr=51.965494,-8.405929&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilbree Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.671258,-8.994595&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilbrin',' ',' ','https://maps.google.co.uk/maps?daddr=52.210644,-8.836935&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilbrittain',' ',' ','https://maps.google.co.uk/maps?daddr=51.671797,-8.688297&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kildorrery',' ',' ','https://maps.google.co.uk/maps?daddr=52.244265,-8.427994&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Killavullen',' ',' ','https://maps.google.co.uk/maps?daddr=52.148435,-8.513256&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Killeagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.939715,-8.001995&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilmacabea',' ',' ','https://maps.google.co.uk/maps?daddr=51.584123,-9.149444&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilmeen',' ',' ','https://maps.google.co.uk/maps?daddr=51.671318,-8.994670&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilmichael',' ',' ','https://maps.google.co.uk/maps?daddr=51.874186,-8.988592&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilmurray GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.834355,-8.877774&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilnamartyra GFC Cill Na Martra',' ',' ','https://maps.google.co.uk/maps?daddr=51.899399,-9.084465&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilshannig',' ',' ','https://maps.google.co.uk/maps?daddr=52.104920,-8.747848&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kilworth',' ',' ','https://maps.google.co.uk/maps?daddr=52.172274,-8.239735&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kinsale',' ',' ','https://maps.google.co.uk/maps?daddr=51.710086,-8.533427&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Kiskeam',' ',' ','https://maps.google.co.uk/maps?daddr=52.177570,-9.155356&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Knocknagree',' ',' ','https://maps.google.co.uk/maps?daddr=52.129668,-9.209558&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Knockscovane',' ',' ','https://maps.google.co.uk/maps?daddr=52.270831,-9.023630&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Liscarroll',' ',' ','https://maps.google.co.uk/maps?daddr=52.260692,-8.799888&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Lisgoold',' ',' ','https://maps.google.co.uk/maps?daddr=51.978014,-8.216722&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Lismire',' ',' ','https://maps.google.co.uk/maps?daddr=52.229344,-8.948268&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Lough Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.893742,-8.521743&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Lyre',' ',' ','https://maps.google.co.uk/maps?daddr=52.127936,-8.899323&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Macroom  (Bishop McEgan Park)',' ',' ','https://maps.google.co.uk/maps?daddr=51.904195,-8.962784&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Macroom  (Tom Creedon Park)',' ',' ','https://maps.google.co.uk/maps?daddr=51.903229,-8.974838&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Mallow',' ',' ','https://maps.google.co.uk/maps?daddr=52.146091,-8.615663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Mannix College School',' ',' ','https://maps.google.co.uk/maps?daddr=52.347583,-8.679553&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Mayfield',' ',' ','https://maps.google.co.uk/maps?daddr=51.910821,-8.417501&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Meelin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.271112,-9.023144&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Midleton',' ',' ','https://maps.google.co.uk/maps?daddr=51.920291,-8.175952&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Milford Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.340695,-8.852899&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Millstreet',' ',' ','https://maps.google.co.uk/maps?daddr=52.060902,-9.066821&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Mitchelstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.276324,-8.281224&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Muintir Bhaire',' ',' ','https://maps.google.co.uk/maps?daddr=51.619002,-9.525812&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Na Piarsaigh',' ',' ','https://maps.google.co.uk/maps?daddr=51.919007,-8.495200&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Naomh Aban',' ',' ','https://maps.google.co.uk/maps?daddr=51.938445,-9.148833&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Nemo Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=51.875948,-8.450782&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Newcestown',' ',' ','https://maps.google.co.uk/maps?daddr=51.781900,-8.872372&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Newmarket',' ',' ','https://maps.google.co.uk/maps?daddr=52.212741,-8.983276&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Newtownshandrum Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.345624,-8.769257&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','ODonovan Rossa (Skibbereen)',' ',' ','https://maps.google.co.uk/maps?daddr=51.554307,-9.272203&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Pairc Ui Chaoimh (Cork County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=51.899535,-8.435054&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Pairc Ui Rinn',' ',' ','https://maps.google.co.uk/maps?daddr=51.891593,-8.437704&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Passage West',' ',' ','https://maps.google.co.uk/maps?daddr=51.866313,-8.344406&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Randal Og',' ',' ','https://maps.google.co.uk/maps?daddr=51.707858,-9.028847&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Rathluirc Hurling Club (Charleville)',' ',' ','https://maps.google.co.uk/maps?daddr=52.352007,-8.679028&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Rathpeacon',' ',' ','https://maps.google.co.uk/maps?daddr=51.937102,-8.484449&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Redmonds',' ',' ','https://maps.google.co.uk/maps?daddr=51.890498,-8.471988&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Rochestown',' ',' ','https://maps.google.co.uk/maps?daddr=51.873149,-8.376651&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Rockchapel',' ',' ','https://maps.google.co.uk/maps?daddr=52.291604,-9.166921&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Russell Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.852971,-8.036134&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Sarsfields Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.928476,-8.387074&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=51.830301,-8.356798&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Shanballymore',' ',' ','https://maps.google.co.uk/maps?daddr=52.219393,-8.477883&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Shandon Rovers Hurling',' ',' ','https://maps.google.co.uk/maps?daddr=51.909156,-8.482234&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Catherines',' ',' ','https://maps.google.co.uk/maps?daddr=52.059252,-8.099488&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Colums',' ',' ','https://maps.google.co.uk/maps?daddr=51.742337,-9.412987&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Finbarrs',' ',' ','https://maps.google.co.uk/maps?daddr=51.877498,-8.498558&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Itas Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=51.909504,-7.899733&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St James',' ',' ','https://maps.google.co.uk/maps?daddr=51.573756,-8.907112&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Marys',' ',' ','https://maps.google.co.uk/maps?daddr=51.735507,-8.936627&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Michaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.889878,-8.393458&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Nicholas',' ',' ','https://maps.google.co.uk/maps?daddr=51.916641,-8.462777&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Oliver Plunketts',' ',' ','https://maps.google.co.uk/maps?daddr=51.709414,-8.896909&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','St Vincents',' ',' ','https://maps.google.co.uk/maps?daddr=51.910636,-8.496444&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Tadgh MacCarthaigh',' ',' ','https://maps.google.co.uk/maps?daddr=51.653069,-9.341254&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Tracton',' ',' ','https://maps.google.co.uk/maps?daddr=51.761764,-8.382633&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Tullylease',' ',' ','https://maps.google.co.uk/maps?daddr=52.312552,-8.937238&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','UCC  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=51.894947,-8.499137&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Urhan',' ',' ','https://maps.google.co.uk/maps?daddr=51.693419,-9.951049&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Valley Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=51.784568,-8.690840&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Watergrasshill Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.021435,-8.337652&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Whitechurch',' ',' ','https://maps.google.co.uk/maps?daddr=51.981874,-8.532043&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Whitescross',' ',' ','https://maps.google.co.uk/maps?daddr=51.953523,-8.440998&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('6','Youghal',' ',' ','https://maps.google.co.uk/maps?daddr=51.952452,-7.859699&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_DERRY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('7','Ardmore  St Marys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.959840,-7.256920&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Ballerin Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.011996,-6.735300&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Ballinascreen St Colms',' ',' ','https://maps.google.co.uk/maps?daddr=54.782189,-6.806009&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Ballinderry Shamrocks GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.658499,-6.560115&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Ballymaguigan St Treas',' ',' ','https://maps.google.co.uk/maps?daddr=54.742355,-6.523486&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Banagher St Marys',' ',' ','https://maps.google.co.uk/maps?daddr=54.892788,-7.014266&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Bellaghy Wolfe Tones GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.806590,-6.514742&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Brian Ógs Steelstown',' ',' ','https://maps.google.co.uk/maps?daddr=55.033464,-7.310189&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Celtic Park (Derry County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.993595,-7.333363&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Claudy John Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.908748,-7.147701&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Coleraine Eoghan Rua',' ',' ','https://maps.google.co.uk/maps?daddr=55.164775,-6.696210&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Doire Colmcille',' ',' ','https://maps.google.co.uk/maps?daddr=55.008210,-7.338728&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Doire Trasna Na Piarsaigh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.984312,-7.301660&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Drumsurn St Mathews GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.988326,-6.864792&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Dungiven St Canices GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.926495,-6.919531&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Erins Own Lavey',' ',' ','https://maps.google.co.uk/maps?daddr=54.830618,-6.604757&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Faughanvale St Marys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.035241,-7.103562&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Foreglen OBriens GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.927339,-7.022785&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Glack OConnors GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.023225,-7.030349&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Glen Watty Grahams GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.839952,-6.689354&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Glenullin John Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.954561,-6.735156&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Greenlough St Oliver Plunketts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.868001,-6.512843&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Kevin Lynchs Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.922610,-6.905540&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Limavady Wolfhounds GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.050406,-6.937534&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Lissan St Michaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.690305,-6.757359&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Loup St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.701490,-6.589533&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Magherafelt ODonovan Rossa GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.765153,-6.586111&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Magilligan St Aidens GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.151382,-6.912836&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Moneymore Henry Joy McCrackens GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.690044,-6.660097&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Na Magha Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=55.034448,-7.299289&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Ogra Colmcille GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.644048,-6.665536&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Owenbeg  Centre of Excellence',' ',' ','https://maps.google.co.uk/maps?daddr=54.923862,-6.951706&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Pádraig Pearses GAC Kilrea',' ',' ','https://maps.google.co.uk/maps?daddr=54.929250,-6.581368&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Robert Emmets GAC Slaughtneil',' ',' ','https://maps.google.co.uk/maps?daddr=54.883575,-6.700190&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Saint Colms GAC Drum',' ',' ','https://maps.google.co.uk/maps?daddr=54.958491,-6.986897&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Sean Dolans GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.994536,-7.352632&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Sean OLearys Newbridge GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.761265,-6.506320&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','St Josephs GFC Craigbane',' ',' ','https://maps.google.co.uk/maps?daddr=54.867612,-7.146510&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','St Malachys GAC Castledawson',' ',' ','https://maps.google.co.uk/maps?daddr=54.768526,-6.539054&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','St Martins Desertmartin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.766743,-6.701778&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','St Marys GFC Slaughtmanus',' ',' ','https://maps.google.co.uk/maps?daddr=54.987261,-7.181561&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','St Patricks College, Maghera',' ',' ','https://maps.google.co.uk/maps?daddr=54.847720,-6.666716&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('7','Swatragh Michael Davitts',' ',' ','https://maps.google.co.uk/maps?daddr=54.916228,-6.664152&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_DONEGAL_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('8','Aodh Ruadh  Club Ballyshannon',' ',' ','https://maps.google.co.uk/maps?daddr=54.498219,-8.192233&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Ardara',' ',' ','https://maps.google.co.uk/maps?daddr=54.769671,-8.415484&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Bun an Phobail / Moville  GFC',' ',' ','https://maps.google.co.uk/maps?daddr=55.194230,-7.022603&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Buncrana  Club (The Scarvey)',' ',' ','https://maps.google.co.uk/maps?daddr=55.129063,-7.456584&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Burt',' ',' ','https://maps.google.co.uk/maps?daddr=55.031355,-7.460865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Carndonagh',' ',' ','https://maps.google.co.uk/maps?daddr=55.259176,-7.246406&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Clg Baile Na NGalloglach (Milford Gaa)',' ',' ','https://maps.google.co.uk/maps?daddr=55.083096,-7.701598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Cloughaneely',' ',' ','https://maps.google.co.uk/maps?daddr=55.140075,-8.098490&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Downings',' ',' ','https://maps.google.co.uk/maps?daddr=55.193391,-7.831632&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Dungloe',' ',' ','https://maps.google.co.uk/maps?daddr=54.951572,-8.352131&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Fanad Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=55.198706,-7.643319&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Four Masters',' ',' ','https://maps.google.co.uk/maps?daddr=54.655209,-8.122330&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Glenfin',' ',' ','https://maps.google.co.uk/maps?daddr=54.824277,-7.923353&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Glenswilly',' ',' ','https://maps.google.co.uk/maps?daddr=54.940961,-7.838509&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Gweedore',' ',' ','https://maps.google.co.uk/maps?daddr=55.082998,-8.310803&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Kilcar',' ',' ','https://maps.google.co.uk/maps?daddr=54.626121,-8.602295&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Killybegs',' ',' ','https://maps.google.co.uk/maps?daddr=54.638170,-8.484813&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Killybegs  (Training)',' ',' ','https://maps.google.co.uk/maps?daddr=54.635393,-8.482314&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Letterkenny Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=54.950377,-7.692865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Lunniagh Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=55.097537,-8.277318&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','MacCumhail Park (Donegal County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.800938,-7.779200&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Malin',' ',' ','https://maps.google.co.uk/maps?daddr=55.297358,-7.262596&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Na Rossa',' ',' ','https://maps.google.co.uk/maps?daddr=54.865568,-8.375788&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Brid',' ',' ','https://maps.google.co.uk/maps?daddr=54.599598,-8.100539&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Colmcille',' ',' ','https://maps.google.co.uk/maps?daddr=55.000038,-7.519895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Columba',' ',' ','https://maps.google.co.uk/maps?daddr=54.710745,-8.731427&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Mhuire',' ',' ','https://maps.google.co.uk/maps?daddr=55.030519,-8.371453&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Michael',' ',' ','https://maps.google.co.uk/maps?daddr=55.182396,-7.987500&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Muire Convoy',' ',' ','https://maps.google.co.uk/maps?daddr=54.858955,-7.678746&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Naille',' ',' ','https://maps.google.co.uk/maps?daddr=54.644458,-8.200350&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Padraig  (Lifford)',' ',' ','https://maps.google.co.uk/maps?daddr=54.837854,-7.478793&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Padraig  (Muff)',' ',' ','https://maps.google.co.uk/maps?daddr=55.094546,-7.232385&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Naomh Ultan',' ',' ','https://maps.google.co.uk/maps?daddr=54.633443,-8.348504&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Noamh Conaill',' ',' ','https://maps.google.co.uk/maps?daddr=54.794438,-8.295965&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Pettigo',' ',' ','https://maps.google.co.uk/maps?daddr=54.545758,-7.838691&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Realt na Mara  (Bundoran)',' ',' ','https://maps.google.co.uk/maps?daddr=54.474826,-8.282683&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Red Hughs',' ',' ','https://maps.google.co.uk/maps?daddr=54.778805,-7.697790&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Robert Emmets GFC Castlefinn',' ',' ','https://maps.google.co.uk/maps?daddr=54.801019,-7.572745&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Sean Mac Cumhaills',' ',' ','https://maps.google.co.uk/maps?daddr=54.801093,-7.777848&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Setanta Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.786458,-7.702296&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','St Eunans',' ',' ','https://maps.google.co.uk/maps?daddr=54.945657,-7.752721&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Termon',' ',' ','https://maps.google.co.uk/maps?daddr=55.044678,-7.815614&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('8','Urris Clonmany',' ',' ','https://maps.google.co.uk/maps?daddr=55.263322,-7.426758&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_DOWN_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('9','Abbey CBS  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.192562,-6.328415&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Aghaderg GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.344929,-6.326038&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','An Riocht',' ',' ','https://maps.google.co.uk/maps?daddr=54.049361,-6.035399&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Annaclone GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.295328,-6.180046&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ardglass GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.268282,-5.594412&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Atticall GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.104829,-6.062372&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Aughlisnafin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.262592,-5.918723&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballela Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.355018,-6.148793&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballycran  Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.477444,-5.507519&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballygalget Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.413505,-5.499505&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballyholland Harps GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.165656,-6.311549&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballykinlar',' ',' ','https://maps.google.co.uk/maps?daddr=54.257588,-5.790980&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Ballymartin GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.073642,-5.961102&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Bredagh GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.573057,-5.910505&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Bright GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.294514,-5.715348&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Bryansford GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.217431,-5.891322&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Burren',' ',' ','https://maps.google.co.uk/maps?daddr=54.137771,-6.262186&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Carryduff GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.530374,-5.887438&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Castlewellan GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.255442,-5.942123&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Clann na Banna',' ',' ','https://maps.google.co.uk/maps?daddr=54.349628,-6.281701&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Clonduff GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.199529,-6.133418&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Dromara GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.368690,-5.986412&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Drumaness',' ',' ','https://maps.google.co.uk/maps?daddr=54.365190,-5.848321&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Drumgath GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.230000,-6.214925&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Dún Phádraig GFC (Downpatrick)',' ',' ','https://maps.google.co.uk/maps?daddr=54.317656,-5.701636&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Dundrum GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.262060,-5.838847&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Glasdrummen GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.124218,-5.907919&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Glenn GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.238698,-6.335517&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Kilclief GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.341430,-5.545360&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Kilcoo Eoghan Rua GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.235995,-6.022836&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Liatroim Fontenoys',' ',' ','https://maps.google.co.uk/maps?daddr=54.285778,-5.994737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Longstone GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.109918,-5.932918&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Loughinisland GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.350654,-5.813634&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Mayobridge GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.182756,-6.224087&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Newry Mitchels GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.153281,-6.323673&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Newry Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=54.161730,-6.334505&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Páirc Esler (Down County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.163169,-6.334176&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Portaferry Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.374090,-5.532775&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Rostrevor',' ',' ','https://maps.google.co.uk/maps?daddr=54.105685,-6.203091&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Saul GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.350104,-5.653110&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Saval GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.214012,-6.282431&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Colmans College  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.187628,-6.339895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St John Bosco GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.182358,-6.346407&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Johns GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.296830,-5.907168&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Josephs Boys High School',' ',' ','https://maps.google.co.uk/maps?daddr=54.184816,-6.338403&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Michaels GAC Magheralin',' ',' ','https://maps.google.co.uk/maps?daddr=54.464894,-6.276165&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Mochais GAC Darragh Cross',' ',' ','https://maps.google.co.uk/maps?daddr=54.448372,-5.757641&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Pauls',' ',' ','https://maps.google.co.uk/maps?daddr=54.636812,-5.840961&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','St Peters  Warrenpoint',' ',' ','https://maps.google.co.uk/maps?daddr=54.103691,-6.228755&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Teconnaught GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.359407,-5.770215&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Tullylish',' ',' ','https://maps.google.co.uk/maps?daddr=54.381576,-6.311270&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('9','Wolfe Tones Killyleagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.403742,-5.652616&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_DUBLIN_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('10','Adamstown',' ',' ','https://maps.google.co.uk/maps?daddr=53.337510,-6.474638&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Allied Irish Banks',' ',' ','https://maps.google.co.uk/maps?daddr=53.407920,-6.242959&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ballinteer St Johns',' ',' ','https://maps.google.co.uk/maps?daddr=53.274869,-6.265426&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ballyboden St Endas',' ',' ','https://maps.google.co.uk/maps?daddr=53.288879,-6.316999&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ballyboughal GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.524716,-6.269052&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ballyfermot DeLa Salle',' ',' ','https://maps.google.co.uk/maps?daddr=53.346305,-6.364721&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ballymun Kickhams',' ',' ','https://maps.google.co.uk/maps?daddr=53.417250,-6.263741&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Bank of Ireland',' ',' ','https://maps.google.co.uk/maps?daddr=53.341303,-6.222532&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Beann Eadair',' ',' ','https://maps.google.co.uk/maps?daddr=53.378823,-6.066803&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Castleknock Porterstown Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.368844,-6.403602&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Castleknock Somerton Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.366431,-6.387649&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Castleknock St. Catherines Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.366719,-6.466430&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Castleknock Tír na nÓg',' ',' ','https://maps.google.co.uk/maps?daddr=53.371120,-6.389194&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Civil Service Football',' ',' ','https://maps.google.co.uk/maps?daddr=53.345280,-6.311120&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Civil Service Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.344966,-6.311302&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clann Mhuire GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.583519,-6.290767&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clanna Gael Fontenoy',' ',' ','https://maps.google.co.uk/maps?daddr=53.338016,-6.216620&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clontarf Adult',' ',' ','https://maps.google.co.uk/maps?daddr=53.369625,-6.182363&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clontarf Juvenile teams',' ',' ','https://maps.google.co.uk/maps?daddr=53.362301,-6.225300&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clontarf St. Pauls College',' ',' ','https://maps.google.co.uk/maps?daddr=53.373168,-6.190973&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Clontarf  Juvenile Pitch 21',' ',' ','https://maps.google.co.uk/maps?daddr=53.370022,-6.193816&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Commericials Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.284665,-6.460261&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Craobh Chiaráin',' ',' ','https://maps.google.co.uk/maps?daddr=53.408745,-6.199604&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Croí Ró Naofa',' ',' ','https://maps.google.co.uk/maps?daddr=53.277169,-6.383486&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Croke Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.360336,-6.250856&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Crumlin',' ',' ','https://maps.google.co.uk/maps?daddr=Crumlin++Club%2C+Lorcan+O%5C%27Toole+Park%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Cuala  Hyde Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.280002,-6.110222&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Cuala Meadowvale',' ',' ','https://maps.google.co.uk/maps?daddr=53.272810,-6.157821&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Cuala Shankill',' ',' ','https://maps.google.co.uk/maps?daddr=53.223045,-6.121230&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Cuala Thomastown',' ',' ','https://maps.google.co.uk/maps?daddr=53.270598,-6.136181&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','DUBLIN CITY UNIVERSITY',' ',' ','https://maps.google.co.uk/maps?daddr=Ballymun+Road%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Erin go Bragh Hazel Bury Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.400936,-6.427581&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Erin go Bragh Hunters Run Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.406647,-6.421981&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Erin go Bragh St. Catherines Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.366713,-6.466184&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Erins Hope',' ',' ','https://maps.google.co.uk/maps?daddr=53.371552,-6.256821&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Erins Isle',' ',' ','https://maps.google.co.uk/maps?daddr=53.384397,-6.298535&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Faughs Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.296273,-6.329520&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Fingal Ravens GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.503721,-6.304511&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Fingallians',' ',' ','https://maps.google.co.uk/maps?daddr=53.466975,-6.212769&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Garda Westmanstown Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.378535,-6.444458&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Garristown',' ',' ','https://maps.google.co.uk/maps?daddr=53.564248,-6.385986&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Geraldine P Morans  Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.267388,-6.163555&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Good Counsel',' ',' ','https://maps.google.co.uk/maps?daddr=53.335873,-6.307284&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Innisfails',' ',' ','https://maps.google.co.uk/maps?daddr=53.410920,-6.184621&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Institute of Technology, Blanchardstown  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.406507,-6.381469&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Kevins Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.330027,-6.294383&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Kilmacud Crokes',' ',' ','https://maps.google.co.uk/maps?daddr=Kilmacud+Crokes+%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Liffey Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.342801,-6.326151&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Lucan Sarsfields',' ',' ','https://maps.google.co.uk/maps?daddr=53.331219,-6.458287&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Man O War GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.551482,-6.188275&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Na Dubh Ghall',' ',' ','https://maps.google.co.uk/maps?daddr=53.394187,-6.135591&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Na Fianna',' ',' ','https://maps.google.co.uk/maps?daddr=53.375130,-6.263870&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Na Gaeil Oga CLG',' ',' ','https://maps.google.co.uk/maps?daddr=53.366277,-6.467471&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Naomh Barrog',' ',' ','https://maps.google.co.uk/maps?daddr=53.389996,-6.151561&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Naomh Fionnbarra',' ',' ','https://maps.google.co.uk/maps?daddr=53.368127,-6.305702&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Naomh Mearnog',' ',' ','https://maps.google.co.uk/maps?daddr=53.437566,-6.143385&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Naomh Olaf',' ',' ','https://maps.google.co.uk/maps?daddr=53.280996,-6.221502&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','National Games Development Centre',' ',' ','https://maps.google.co.uk/maps?daddr=53.397405,-6.367650&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','ODwyers',' ',' ','https://maps.google.co.uk/maps?daddr=53.617217,-6.195216&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','OTooles',' ',' ','https://maps.google.co.uk/maps?daddr=53.397430,-6.184348&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Park Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=53.350330,-6.329168&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Parnell Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.373133,-6.216953&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Parnells',' ',' ','https://maps.google.co.uk/maps?daddr=53.388652,-6.205913&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Pavee',' ',' ','https://maps.google.co.uk/maps?daddr=53.366930,-6.275318&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Plunket College Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=Plunket+College%2C+Swords+Road%2C+Whitehall%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Portobello GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.325455,-6.271332&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Raheny Pitch 12',' ',' ','https://maps.google.co.uk/maps?daddr=53.375623,-6.169113&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Raheny Pitch 29',' ',' ','https://maps.google.co.uk/maps?daddr=53.370323,-6.184552&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Raheny Pitch 9 10',' ',' ','https://maps.google.co.uk/maps?daddr=53.375124,-6.172750&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Ranelagh Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.302370,-6.294688&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Robert Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.310670,-6.322535&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Rosmini Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.399375,-6.283557&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Rosmini Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.377810,-6.249716&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Rosmini Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.377810,-6.249716&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Rosmini Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.377810,-6.249716&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Rosmini Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.377810,-6.249716&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Round Towers Clondalkin',' ',' ','https://maps.google.co.uk/maps?daddr=Round+Towers++Club%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Round Towers Lusk GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.527605,-6.177760&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Scoil Ui Chonaill',' ',' ','https://maps.google.co.uk/maps?daddr=53.361495,-6.207050&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Setanta Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=Setanta++Club%2C+Ballymun%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Skerries Harps',' ',' ','https://maps.google.co.uk/maps?daddr=53.577958,-6.113055&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Annes GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.269968,-6.355559&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Brendans',' ',' ','https://maps.google.co.uk/maps?daddr=53.354509,-6.283815&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Brigids',' ',' ','https://maps.google.co.uk/maps?daddr=53.376941,-6.352957&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Colmcilles Balheary GFC',' ',' ','https://maps.google.co.uk/maps?daddr=St+Colmcilles++Club%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Finians Newcastle GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.303550,-6.485362&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Finians Swords',' ',' ','https://maps.google.co.uk/maps?daddr=St+Finians++Club%2C+Swords%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Francis Gaels Cabinteely GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.260169,-6.139920&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St James Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.329207,-6.306238&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Josephs OConnell Boys',' ',' ','https://maps.google.co.uk/maps?daddr=53.361283,-6.235240&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Judes',' ',' ','https://maps.google.co.uk/maps?daddr=53.296318,-6.329482&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Kevins Killians GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.310083,-6.374913&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Margarets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.441861,-6.300788&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Marks',' ',' ','https://maps.google.co.uk/maps?daddr=53.289078,-6.388561&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Marys Saggart GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.279219,-6.441518&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Maurs',' ',' ','https://maps.google.co.uk/maps?daddr=53.532235,-6.106821&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Monicas',' ',' ','https://maps.google.co.uk/maps?daddr=53.388617,-6.178533&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Oliver Plunketts Eoghan Ruadh',' ',' ','https://maps.google.co.uk/maps?daddr=53.374445,-6.324703&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Patricks Donabate',' ',' ','https://maps.google.co.uk/maps?daddr=53.485218,-6.130398&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Patricks Palmerstown',' ',' ','https://maps.google.co.uk/maps?daddr=Glenaulin+Park%2C+Dublin%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Peregrines',' ',' ','https://maps.google.co.uk/maps?daddr=53.394586,-6.405689&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Sylvesters',' ',' ','https://maps.google.co.uk/maps?daddr=53.438800,-6.151072&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','St Vincents',' ',' ','https://maps.google.co.uk/maps?daddr=53.373972,-6.228733&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Starlights GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.417340,-6.261609&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Stars Of Erin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=Ballybrack+Road%2C+Glencullen%2C+Ireland&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Templeogue Synge Street GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.330251,-6.294544&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Thomas Davis',' ',' ','https://maps.google.co.uk/maps?daddr=53.272310,-6.362575&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Trinity Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.414296,-6.164736&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','UCD GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.306089,-6.229345&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Wanderers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.260275,-6.302215&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Whitehall Colmcille',' ',' ','https://maps.google.co.uk/maps?daddr=53.412998,-6.240460&t=m&layer=1&doflg=ptk&om =1',' '),"+
                    "('10','Wild Geese',' ',' ','https://maps.google.co.uk/maps?daddr=53.526307,-6.314853&t=m&layer=1&doflg=ptk&om =1',' ');"
            ;

    private static final String INITIAL_FERMANAGH_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('11','Aghadrumsee St McCartans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.236014,-7.234840&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Bawnacre (3g Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=Bawnacre+Centre%2C+Castle+Street%2C+Irvinestown%2C+United+Kingdom&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Belcoo ORaghallaighs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.298727,-7.882293&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Belnaleck Art McMurroughs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.290324,-7.672319&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Brewster Park (Fermanagh County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.350404,-7.634597&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Brookeboro Heber McMahons GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.312828,-7.392971&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Coa ODwyers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.402808,-7.537061&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Derrygonnelly Harps GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.421564,-7.827308&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Derrylin OConnells GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.204380,-7.576758&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Devinish St Marys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.410071,-8.090519&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Enniskillen Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.350479,-7.634511&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Erne Gaels Belleek GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.484481,-8.088212&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Irvinestown St Molaise GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.474551,-7.630477&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Kinawley Brian Borus GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.217707,-7.676868&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Knocks Grattans Hurling Club (Not Longer Playing)',' ',' ','https://maps.google.co.uk/maps?daddr=54.269428,-7.375785&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Lisbellaw St Patricks Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.345739,-7.551781&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Lisnaskea Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=54.249512,-7.451209&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Lissan County Training Facilty',' ',' ','https://maps.google.co.uk/maps?daddr=54.374327,-7.573625&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Maguiresbridge St Marys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.290894,-7.477773&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Newtownbutler First Fermanghs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.187170,-7.363855&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Roslea Shamrocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.239958,-7.170081&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','St Josephs Ederney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.531766,-7.654831&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','St Patricks Donagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.215900,-7.389604&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Teemore Shamrocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.144377,-7.567488&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('11','Tempo Maguires GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.381863,-7.459996&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_GALWAY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('12','Abbeyknockmoy Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.438237,-8.726792&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Ahascragh Fohenagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.400194,-8.324525&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Annaghdown',' ',' ','https://maps.google.co.uk/maps?daddr=53.388748,-8.958417&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Aran Island  (Oileáin Árann Inishmore)',' ',' ','https://maps.google.co.uk/maps?daddr=53.105658,-9.655244&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Aran Islands  (Oileáin Árann Inisheer)',' ',' ','https://maps.google.co.uk/maps?daddr=53.064555,-9.522732&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Athenry Vocational College  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.302294,-8.743916&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Ballinakill',' ',' ','https://maps.google.co.uk/maps?daddr=53.089192,-8.455396&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Ballinderreen Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.183092,-8.906779&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Ballygar Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.529072,-8.321865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Ballygar St Brendans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.529097,-8.321457&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Barna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.267590,-9.162512&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Barnaderg',' ',' ','https://maps.google.co.uk/maps?daddr=53.481751,-8.722597&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Beagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.016623,-8.823041&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Bearna/Na Forbacha Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.258969,-9.208356&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Belclare',' ',' ','https://maps.google.co.uk/maps?daddr=53.489297,-8.921220&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Bullaun',' ',' ','https://maps.google.co.uk/maps?daddr=53.249700,-8.556719&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Caherlistrane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.490739,-9.019389&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Caltra',' ',' ','https://maps.google.co.uk/maps?daddr=53.436748,-8.436770&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cappataggle Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.270975,-8.409696&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cárna / Caiseal GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.323241,-9.830307&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Carnmore Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.306750,-8.905492&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Carraroe (An Cheathrú Rua)',' ',' ','https://maps.google.co.uk/maps?daddr=53.256992,-9.603274&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Castlegar Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.276582,-8.976924&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cinn Mhara',' ',' ','https://maps.google.co.uk/maps?daddr=53.136586,-8.952495&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Claregalway (Training Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.366207,-8.914526&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Claregalway GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.337779,-8.939116&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Clarinbridge',' ',' ','https://maps.google.co.uk/maps?daddr=53.230958,-8.876181&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','CLG An Spidéal',' ',' ','https://maps.google.co.uk/maps?daddr=53.246741,-9.278963&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Coolarne',' ',' ','https://maps.google.co.uk/maps?daddr=53.367295,-8.840250&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Corofin',' ',' ','https://maps.google.co.uk/maps?daddr=53.436978,-8.860817&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cortoon Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=53.564923,-8.799641&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cumann Micheal Breathnach',' ',' ','https://maps.google.co.uk/maps?daddr=53.247511,-9.367969&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Cumann Peile na bPearsaigh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.386118,-9.611052&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Duggan Park (Ballinasloe)',' ',' ','https://maps.google.co.uk/maps?daddr=53.330790,-8.228459&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Dunmore McHales',' ',' ','https://maps.google.co.uk/maps?daddr=53.615505,-8.742220&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Father Griffins Eire Og',' ',' ','https://maps.google.co.uk/maps?daddr=53.266124,-9.054381&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Fohenagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.385600,-8.388577&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Glenamaddy GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.605729,-8.553275&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Glinsk GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.651989,-8.454881&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','GMIT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.276762,-9.011890&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Gort',' ',' ','https://maps.google.co.uk/maps?daddr=53.044574,-8.835325&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Headford',' ',' ','https://maps.google.co.uk/maps?daddr=53.472748,-9.096084&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kenny Memorial Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.296100,-8.750525&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kilbeacanty Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.064938,-8.767970&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kilconieron',' ',' ','https://maps.google.co.uk/maps?daddr=53.228549,-8.629643&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kilconly',' ',' ','https://maps.google.co.uk/maps?daddr=53.553936,-8.912669&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','KIlkerrin/Clonberne GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.558557,-8.648686&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Killannin Gaa (Chill Ainnin Ros Cathail)',' ',' ','https://maps.google.co.uk/maps?daddr=53.387302,-9.226241&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Killimor Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.172116,-8.292092&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Killimordaly',' ',' ','https://maps.google.co.uk/maps?daddr=53.302326,-8.623441&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kilnadeema Leitrim Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.163980,-8.562984&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Kiltormer',' ',' ','https://maps.google.co.uk/maps?daddr=53.236140,-8.275065&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Lar Ionad C.L.G., Loughgeorge',' ',' ','https://maps.google.co.uk/maps?daddr=53.356302,-8.928473&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Leitrim',' ',' ','https://maps.google.co.uk/maps?daddr=53.174444,-8.474633&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Liam Mellows Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.271219,-9.021857&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Loughrea Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.202826,-8.551462&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Maree  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.229542,-8.951637&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Meelick Eyrecourt Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.211905,-8.092664&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Menlo Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=53.294106,-9.045031&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Menlough',' ',' ','https://maps.google.co.uk/maps?daddr=53.423913,-8.589624&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Milltown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.614697,-8.903990&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Moneen  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.284845,-9.032865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Monivea Abbeyknockmoy GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.377421,-8.699219&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Mountbellew Moylough',' ',' ','https://maps.google.co.uk/maps?daddr=53.473329,-8.504609&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Moycullen',' ',' ','https://maps.google.co.uk/maps?daddr=53.359215,-9.176266&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Moylough  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.481936,-8.571289&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Mullagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.223373,-8.397149&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Naomh Anna Leitir Mór GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.278000,-9.659150&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Naomh Columba Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.275659,-9.018681&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Naomh Feichin  Clifden',' ',' ','https://maps.google.co.uk/maps?daddr=53.491228,-10.016624&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Naomh Muire Ardrahan Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.156698,-8.803053&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','NUIG  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.293839,-9.073709&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Oranmore/Maree',' ',' ','https://maps.google.co.uk/maps?daddr=53.272085,-8.919450&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Oughterard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.428528,-9.315580&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Padraig Pearses GAC',' ',' ','https://maps.google.co.uk/maps?daddr=53.390085,-8.475437&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Pearse Stadium (Galway County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.263281,-9.085168&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Portumna Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.088663,-8.218127&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Raheen  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.305371,-8.758281&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Rahoon Newcastle Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.290694,-9.122107&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Renvyle',' ',' ','https://maps.google.co.uk/maps?daddr=53.590636,-9.964095&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Rossaveal',' ',' ','https://maps.google.co.uk/maps?daddr=53.271777,-9.532045&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Salthill Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.264045,-9.133480&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Salthill Knocknacarra GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.263240,-9.086611&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Sarsfields Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.301704,-8.490629&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Sean McDermott Hurling Club Craughwell',' ',' ','https://maps.google.co.uk/maps?daddr=53.230072,-8.730344&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Skehana Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.423695,-8.589753&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Colmans Gort GFC',' ',' ','https://maps.google.co.uk/maps?daddr=Gort++Club%2C+Galway%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Endas College',' ',' ','https://maps.google.co.uk/maps?daddr=53.264741,-9.090747&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Gabriels  Kilconnell',' ',' ','https://maps.google.co.uk/maps?daddr=53.329624,-8.406022&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Grellans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.330982,-8.228867&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St James Galway GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.282875,-9.014668&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Kerrills GFC (Disbanded)',' ',' ','https://maps.google.co.uk/maps?daddr=53.372989,-8.570200&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Marys Athenry',' ',' ','https://maps.google.co.uk/maps?daddr=53.324478,-8.784910&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Michaels Galway GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.275537,-9.077792&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Patricks Clonbur GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.550055,-9.376928&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St Thomas Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.141442,-8.705356&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','St. Kerrills  Pitch (Gurteen)',' ',' ','https://maps.google.co.uk/maps?daddr=53.373063,-8.570119&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Sylane Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.528989,-8.936294&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tommy Larkins Hurling Club Woodford',' ',' ','https://maps.google.co.uk/maps?daddr=53.050727,-8.397814&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tuam  Stadium (Galway County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.509712,-8.853317&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tuam Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.510082,-8.834957&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tuam Stars GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.508945,-8.837057&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Turloughmore Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.367447,-8.904521&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tynagh Abbey Duniry Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.153458,-8.377025&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Tynagh-Abbey/Duniry',' ',' ','https://maps.google.co.uk/maps?daddr=53.130783,-8.408017&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('12','Williamstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.675976,-8.581973&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_KERRY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('13','Abbeydorney Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.348894,-9.690371&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','An Gaeltacht',' ',' ','https://maps.google.co.uk/maps?daddr=52.181129,-10.364410&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Annascaul',' ',' ','https://maps.google.co.uk/maps?daddr=52.153438,-10.047061&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ardfert GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.331530,-9.778122&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Austin Stack Park (County Hurling Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.269978,-9.693663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Austin Stacks',' ',' ','https://maps.google.co.uk/maps?daddr=52.270455,-9.696196&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ballydonoghue GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.486204,-9.519728&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ballyduff',' ',' ','https://maps.google.co.uk/maps?daddr=52.451603,-9.670136&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ballyheigue Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.386903,-9.832249&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ballylongford ORahillys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.544270,-9.475617&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ballymacelligott GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.252666,-9.598285&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Beale GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.506067,-9.672292&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Beaufort GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.068295,-9.639280&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Brosna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.310420,-9.260509&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Caherslee (Kerry County Board Owned)',' ',' ','https://maps.google.co.uk/maps?daddr=52.274014,-9.716007&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Castlegregory',' ',' ','https://maps.google.co.uk/maps?daddr=52.259207,-10.015798&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Castleisland Desmonds',' ',' ','https://maps.google.co.uk/maps?daddr=52.238214,-9.463305&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Causeway Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.410476,-9.735571&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Churchill Pearse Bros',' ',' ','https://maps.google.co.uk/maps?daddr=52.274972,-9.783540&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Clounmacon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.482231,-9.422568&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Cordal',' ',' ','https://maps.google.co.uk/maps?daddr=52.221700,-9.382818&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Cromane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.102881,-9.899969&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Crotta ONeills Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.362799,-9.634945&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Currow GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.180083,-9.506865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Derrynane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.764616,-10.104074&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Dingle GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.137090,-10.268269&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Dr Crokes',' ',' ','https://maps.google.co.uk/maps?daddr=52.066535,-9.503978&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Dromid Pearses',' ',' ','https://maps.google.co.uk/maps?daddr=51.893821,-10.087767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Duagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.412636,-9.388525&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Feale Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=52.447243,-9.476096&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Finuge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.424878,-9.532796&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Firies GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.172682,-9.547366&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Fitzgerald Stadium (Kerry County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.065888,-9.509708&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Fossa GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.068454,-9.540800&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Glenbeigh/Glencar GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.058009,-9.935932&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Glenflesk GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.014888,-9.361746&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Gneeveguilla GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.119531,-9.272032&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Gortmore  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=51.971085,-10.145536&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','IT Tralee  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.271716,-9.693332&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','John Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.248193,-9.676552&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Keel GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.172846,-9.774474&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Kenmare',' ',' ','https://maps.google.co.uk/maps?daddr=51.880644,-9.570014&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Kerins ORahillys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.267409,-9.716055&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Kilcummin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.097997,-9.474796&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Kilgarvin',' ',' ','https://maps.google.co.uk/maps?daddr=51.904017,-9.452180&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Kilmoyley Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.360028,-9.773390&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Knockanure GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.457487,-9.373655&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Knocknagoshel GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.323045,-9.382035&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Ladys Walk Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.449608,-9.672518&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Laune Rangers (Cloon Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=52.115282,-9.773723&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Laune Rangers (Main Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=52.104838,-9.800277&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Legion GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.072074,-9.508227&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Lispole GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.142931,-10.143900&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Listowel Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.447398,-9.477156&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Listry GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.104291,-9.632607&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Lixnaw Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.402720,-9.635385&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Miltown Castlemaine GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.149771,-9.716549&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Moyvane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.499118,-9.377142&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Na Gaeil',' ',' ','https://maps.google.co.uk/maps?daddr=52.282593,-9.689180&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Rathmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.065044,-9.240285&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Renard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.929997,-10.251381&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Scartaglin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.186359,-9.416839&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Skellig Rangers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.880399,-10.369956&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Sneem GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.842214,-9.901600&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Spa GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.070096,-9.468659&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Brendans Hurling Club Ardfert',' ',' ','https://maps.google.co.uk/maps?daddr=52.331668,-9.777972&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Marys Asdee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=asdee+&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Marys Cahirsiveen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.945072,-10.233400&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Michaels Foilmore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.829446,-10.268676&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Patricks Blennersville GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.254223,-9.734273&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','St Senans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.396233,-9.514879&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Tarbert GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.571127,-9.373623&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Templenoe GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.861307,-9.687667&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Tuosist GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.765572,-9.766036&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Valentia Young Irelanders GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.905923,-10.337679&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('13','Waterville GFC',' ',' ','https://maps.google.co.uk/maps?daddr=51.825600,-10.162568&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_KILDARE_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('14','Allenwood GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.289575,-6.873794&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ardclough',' ',' ','https://maps.google.co.uk/maps?daddr=53.297203,-6.566595&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Athgarvan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.148920,-6.800365&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Athy',' ',' ','https://maps.google.co.uk/maps?daddr=52.993239,-6.969001&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ballykelly',' ',' ','https://maps.google.co.uk/maps?daddr=53.148620,-7.062176&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ballymore Eustace GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.129409,-6.587232&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ballyteague GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.265559,-6.866326&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Broadford Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.389721,-6.995705&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Cappagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.408023,-6.787469&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Caragh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.284094,-6.763533&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Carbury GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.344172,-6.928049&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Castledermot GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.910442,-6.845373&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Castlemitchel GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.022806,-7.032741&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Cellbridge',' ',' ','https://maps.google.co.uk/maps?daddr=53.330803,-6.528550&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Clane',' ',' ','https://maps.google.co.uk/maps?daddr=53.289357,-6.692841&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Clogherinkoe',' ',' ','https://maps.google.co.uk/maps?daddr=53.389426,-6.996038&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Coill Dubh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.294856,-6.821126&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Confey',' ',' ','https://maps.google.co.uk/maps?daddr=53.375136,-6.483028&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Eadestown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.197794,-6.585553&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Eire Og GFC Corrachoill Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.255021,-6.737108&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ellistown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.184976,-6.983775&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Grangenolvin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.966256,-6.955472&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','HawkField (County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.196457,-6.827767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Johnstownbridge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.392315,-6.846033&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Kilcock',' ',' ','https://maps.google.co.uk/maps?daddr=53.394436,-6.667907&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Kilcullen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.127442,-6.754768&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Kildangan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.099861,-7.000962&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Kill',' ',' ','https://maps.google.co.uk/maps?daddr=53.244847,-6.598256&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Leixlilip  (2nd pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=53.364907,-6.521115&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Leixlip',' ',' ','https://maps.google.co.uk/maps?daddr=53.364600,-6.499529&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Maynooth',' ',' ','https://maps.google.co.uk/maps?daddr=53.388409,-6.598964&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Maynooth  (Second Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=53.392594,-6.598932&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Milltown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.204285,-6.854964&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Monasterevin',' ',' ','https://maps.google.co.uk/maps?daddr=53.140155,-7.054446&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Moorefield',' ',' ','https://maps.google.co.uk/maps?daddr=53.172470,-6.850179&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Moorefield  Nursery Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.173138,-6.817650&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Naas',' ',' ','https://maps.google.co.uk/maps?daddr=53.229962,-6.662704&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Naomh Brid Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.160373,-6.914314&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Nurney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.103777,-6.940624&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Raheens GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.234272,-6.719363&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Rathangan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.226372,-6.989815&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Rathcoffey GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.332923,-6.690105&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Rheban GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.026806,-6.998924&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Robertstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.277102,-6.821008&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Ros Glas Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.135160,-7.042537&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Round Towers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.160452,-6.914413&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Sallins',' ',' ','https://maps.google.co.uk/maps?daddr=53.250072,-6.662328&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Sarsfields',' ',' ','https://maps.google.co.uk/maps?daddr=53.188351,-6.813251&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','St Conleths Park (Kildare County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.180109,-6.795076&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','St Kevins GFC Staplestown',' ',' ','https://maps.google.co.uk/maps?daddr=53.330783,-6.760143&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','St Laurences',' ',' ','https://maps.google.co.uk/maps?daddr=53.041774,-6.858559&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Straffan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.311634,-6.606511&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Suncroft GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.106552,-6.858508&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('14','Two Mile House GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.161761,-6.690266&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_KILKENNY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('15','Ballyhale Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=52.469273,-7.203421&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Barrow Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=52.683202,-7.026250&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Bennettsbridge',' ',' ','https://maps.google.co.uk/maps?daddr=52.594602,-7.181593&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Black & Whites',' ',' ','https://maps.google.co.uk/maps?daddr=52.591369,-6.959442&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Carrickshock',' ',' ','https://maps.google.co.uk/maps?daddr=52.453616,-7.250462&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Carrigeen Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.287962,-7.222996&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Clara',' ',' ','https://maps.google.co.uk/maps?daddr=52.646032,-7.145416&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Cloneen Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.868042,-7.166348&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Conahy Shamrocks Jenkinstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.720581,-7.292680&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Danesfort',' ',' ','https://maps.google.co.uk/maps?daddr=52.583156,-7.240237&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Dicksboro',' ',' ','https://maps.google.co.uk/maps?daddr=52.657331,-7.272713&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Dunnamaggin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.499667,-7.291821&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Emeralds  Urlingford',' ',' ','https://maps.google.co.uk/maps?daddr=52.724753,-7.576017&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Erins Own Castlecomer',' ',' ','https://maps.google.co.uk/maps?daddr=52.784706,-7.213533&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Fenians Hurling Club Johnstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.752945,-7.564269&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Galmoy Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.791863,-7.569784&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Glenmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.342786,-7.058737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Graigue Ballycallan',' ',' ','https://maps.google.co.uk/maps?daddr=52.618084,-7.421157&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Graiguenamanagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.545650,-6.960601&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','James Stephens',' ',' ','https://maps.google.co.uk/maps?daddr=52.643431,-7.244448&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','John Lockes Hurling Club Callan',' ',' ','https://maps.google.co.uk/maps?daddr=52.540564,-7.387807&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Kilmacow Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.310361,-7.173606&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Kilmoganny GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.499582,-7.291875&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Lisdowney',' ',' ','https://maps.google.co.uk/maps?daddr=52.789852,-7.390687&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Mooncoin',' ',' ','https://maps.google.co.uk/maps?daddr=52.294334,-7.259270&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Muckalee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.746678,-7.179619&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Mullinavat',' ',' ','https://maps.google.co.uk/maps?daddr=52.366743,-7.176025&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Nowlan Park (Kilkenny County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.656498,-7.239218&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','OLoughlin Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=52.658047,-7.236300&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Piltown',' ',' ','https://maps.google.co.uk/maps?daddr=52.359923,-7.329040&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Railyard Moneenroe GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.838486,-7.154331&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Rower  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.453028,-6.957307&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Rower-Inistioge',' ',' ','https://maps.google.co.uk/maps?daddr=52.489399,-7.063962&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Slieverue',' ',' ','https://maps.google.co.uk/maps?daddr=52.286308,-7.070378&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','St Lachtains Freshford Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.730809,-7.394121&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','St Martins Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.789060,-7.120407&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','St Patricks Ballyragget',' ',' ','https://maps.google.co.uk/maps?daddr=52.780540,-7.334576&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Thomastown',' ',' ','https://maps.google.co.uk/maps?daddr=52.522312,-7.129902&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Threecastles Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.704189,-7.312925&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Tullaroan',' ',' ','https://maps.google.co.uk/maps?daddr=52.660780,-7.444063&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Tullogher Rosbercon',' ',' ','https://maps.google.co.uk/maps?daddr=52.413920,-7.020234&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Windgap',' ',' ','https://maps.google.co.uk/maps?daddr=52.462329,-7.396006&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('15','Young Irelands Gowran',' ',' ','https://maps.google.co.uk/maps?daddr=52.631650,-7.053094&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;



    private static final String INITIAL_LAOIS_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('16','Annanough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.051365,-7.090366&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Arles Kilcruise GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.894969,-7.025156&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Arles Killeen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.892555,-6.995169&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballinakill',' ',' ','https://maps.google.co.uk/maps?daddr=52.880637,-7.308666&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballyfin',' ',' ','https://maps.google.co.uk/maps?daddr=53.057343,-7.409098&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballylynan',' ',' ','https://maps.google.co.uk/maps?daddr=52.939192,-7.039543&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballypickas Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.912441,-7.294718&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballyroan Abbey GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.943233,-7.293012&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Ballyskenagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.958941,-7.842339&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Barrowhouse GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.947688,-6.994107&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Borris In Ossory Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.940835,-7.618010&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Camross',' ',' ','https://maps.google.co.uk/maps?daddr=52.996842,-7.562188&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Castletown Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.980418,-7.503040&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Clonad Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.993736,-7.297282&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Clonaslee St Manmans',' ',' ','https://maps.google.co.uk/maps?daddr=53.150888,-7.525860&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Clough Ballacolla',' ',' ','https://maps.google.co.uk/maps?daddr=52.881627,-7.450892&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Courtwood',' ',' ','https://maps.google.co.uk/maps?daddr=53.103423,-7.084240&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Crettyard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.865814,-7.104871&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Emo GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.099023,-7.208362&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Errill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.867802,-7.681085&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Graiguecullen',' ',' ','https://maps.google.co.uk/maps?daddr=52.841577,-6.941739&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Kilcavan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.195718,-7.368897&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Kilcotton Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.931226,-7.577262&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Killeshin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.853875,-6.997594&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Kyle Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.959742,-7.691964&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Mountmellick',' ',' ','https://maps.google.co.uk/maps?daddr=53.103945,-7.304256&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','ODempseys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.119079,-7.150769&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','OMoore Park (Laois County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.025567,-7.301799&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Park/Ratheniska',' ',' ','https://maps.google.co.uk/maps?daddr=53.007044,-7.217438&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Portarlington GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.148726,-7.180241&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Portlaoise',' ',' ','https://maps.google.co.uk/maps?daddr=53.025413,-7.266415&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Rathdowney Errill Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.853176,-7.586821&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Rathdowney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.853290,-7.586678&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Rosenallis',' ',' ','https://maps.google.co.uk/maps?daddr=53.138977,-7.416008&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Shanahoe',' ',' ','https://maps.google.co.uk/maps?daddr=52.909038,-7.406384&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Slieve Bloom Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.032420,-7.502825&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Spink GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.906605,-7.266662&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','St Fintans Colt',' ',' ','https://maps.google.co.uk/maps?daddr=52.966476,-7.369981&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','St Fintans Mountrath',' ',' ','https://maps.google.co.uk/maps?daddr=53.001859,-7.477151&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','St Josephs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.989384,-7.090774&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','St Lazerians Abbeyleix Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.912318,-7.353587&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Stradbally GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.009142,-7.145823&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','The Harps Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.850092,-7.395719&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','The Heath GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.066150,-7.216129&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','The Rock GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.116213,-7.295158&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Timahoe',' ',' ','https://maps.google.co.uk/maps?daddr=52.969403,-7.211806&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('16','Trumera Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.976194,-7.429440&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;


    private static final String INITIAL_LEITRIM_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('17','Allen Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.045352,-8.031408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Annaduff GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.894116,-7.950840&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Aughavas GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.959557,-7.723550&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Aughawillan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.092001,-7.758708&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Aughnasheelan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.064948,-7.865374&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Ballinaglera GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.159513,-8.013582&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Bornacoola GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.861158,-7.913847&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Carrigallen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.985903,-7.656333&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Cloone GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.934660,-7.786571&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Dromahaire St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.233111,-8.310256&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Drumkeerin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.166668,-8.134829&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Drumreilly GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.039389,-7.744364&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Eslin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.933839,-7.920456&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Fenagh St Caillins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.015530,-7.902013&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Glencar Manorhamilton',' ',' ','https://maps.google.co.uk/maps?daddr=54.306850,-8.173147&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Glenfarne/Kiltyclogher',' ',' ','https://maps.google.co.uk/maps?daddr=54.289310,-7.985977&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Gortletteragh',' ',' ','https://maps.google.co.uk/maps?daddr=53.883114,-7.781357&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Kiltubrid GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.034317,-7.948791&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Leitrim Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.996954,-8.056455&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Melvin Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.443955,-8.289614&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Mohill',' ',' ','https://maps.google.co.uk/maps?daddr=53.918949,-7.864226&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Páirc Seán Mac Diarmada (Leitrim County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.947594,-8.076228&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','Sean OHeslins Ballinamore',' ',' ','https://maps.google.co.uk/maps?daddr=54.059671,-7.793008&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','St Marys Kiltoghert',' ',' ','https://maps.google.co.uk/maps?daddr=53.947423,-8.074093&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('17','St Osnetts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.320822,-8.201841&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_LIMERICK_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('18','Abbey Sarsfield (Bishops Field)',' ',' ','https://maps.google.co.uk/maps?daddr=St+Munchin%5C%27s+College%2C+Limerick+City%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Abbey Sarsfields (Shannon Fields)',' ',' ','https://maps.google.co.uk/maps?daddr=52.676160,-8.602731&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Adare',' ',' ','https://maps.google.co.uk/maps?daddr=52.563478,-8.807065&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ahane',' ',' ','https://maps.google.co.uk/maps?daddr=52.691413,-8.515220&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ardscoil Rís  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.665026,-8.641656&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Askeaton Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.599659,-8.968191&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Athea GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.462677,-9.273405&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballinacurra Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.643858,-8.635941&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballybricken Bohermore',' ',' ','https://maps.google.co.uk/maps?daddr=52.550941,-8.498011&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballybrown',' ',' ','https://maps.google.co.uk/maps?daddr=52.631956,-8.728595&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballybrown (New Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=52.636130,-8.731899&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballylanders GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.370896,-8.351433&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Ballysteen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.646143,-8.936895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Banogue GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.478350,-8.681903&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Barrack Field  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.653781,-8.632293&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Blackrock',' ',' ','https://maps.google.co.uk/maps?daddr=52.362039,-8.473399&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Boher Field (New Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=52.619719,-8.476574&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Bruff',' ',' ','https://maps.google.co.uk/maps?daddr=52.475429,-8.541001&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Bruree',' ',' ','https://maps.google.co.uk/maps?daddr=52.422124,-8.649791&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Caherconlish GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.587256,-8.465878&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Caherline Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.587289,-8.465878&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Camogue Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.510932,-8.616318&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Cappagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.551893,-8.937989&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Cappamore',' ',' ','https://maps.google.co.uk/maps?daddr=52.621608,-8.338666&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Castlemahon',' ',' ','https://maps.google.co.uk/maps?daddr=52.411432,-8.969489&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Castletown Ballyagran',' ',' ','https://maps.google.co.uk/maps?daddr=52.401221,-8.785983&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Claughaun',' ',' ','https://maps.google.co.uk/maps?daddr=52.658899,-8.594720&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Crecora Manister',' ',' ','https://maps.google.co.uk/maps?daddr=52.572946,-8.665177&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Croagh  Hurling Club Kilfinny',' ',' ','https://maps.google.co.uk/maps?daddr=52.532808,-8.860849&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Croom',' ',' ','https://maps.google.co.uk/maps?daddr=52.513786,-8.728756&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Doon Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.600923,-8.247299&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Dromcollogher Broadford',' ',' ','https://maps.google.co.uk/maps?daddr=52.344234,-8.916167&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Dromin Athlacca Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.456284,-8.646369&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Effin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.357623,-8.609987&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Fedamore',' ',' ','https://maps.google.co.uk/maps?daddr=52.543086,-8.611962&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Feenagh Kilmeedy',' ',' ','https://maps.google.co.uk/maps?daddr=52.393365,-8.897080&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Feohanagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.411405,-8.969564&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Fr Caseys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.388173,-9.304905&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Gaelic Grounds (Limerick County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.669916,-8.653493&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Galbally GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.398164,-8.297939&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Galtee Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.299812,-8.213450&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Garryspillane Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.433684,-8.415602&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Gerald Griffins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.562480,-9.166653&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Glenroe Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.323268,-8.413006&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Glin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.563654,-9.281387&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Granagh Ballingarry',' ',' ','https://maps.google.co.uk/maps?daddr=52.472305,-8.858972&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Hospital Herbertstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.476677,-8.433799&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Kilcornan',' ',' ','https://maps.google.co.uk/maps?daddr=52.614684,-8.883154&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Kildimo',' ',' ','https://maps.google.co.uk/maps?daddr=52.615126,-8.809780&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Kileedy',' ',' ','https://maps.google.co.uk/maps?daddr=52.376896,-9.042960&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Kilmallock',' ',' ','https://maps.google.co.uk/maps?daddr=52.397830,-8.574572&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Kilteely Dromkeen',' ',' ','https://maps.google.co.uk/maps?daddr=52.524910,-8.392729&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Knockaderry',' ',' ','https://maps.google.co.uk/maps?daddr=52.463096,-8.968245&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Knockainey',' ',' ','https://maps.google.co.uk/maps?daddr=52.474031,-8.495661&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Knockane',' ',' ','https://maps.google.co.uk/maps?daddr=Garrydoolis+National+School%2C+Pallasgreen%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Limerick IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.676955,-8.648633&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Mary Immaculate College  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.653713,-8.645886&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Mick Neville Park',' ',' ','https://maps.google.co.uk/maps?daddr=52.525021,-8.929138&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Milford',' ',' ','https://maps.google.co.uk/maps?daddr=52.676265,-8.559422&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Monagea',' ',' ','https://maps.google.co.uk/maps?daddr=52.417128,-9.090806&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Monaleen',' ',' ','https://maps.google.co.uk/maps?daddr=52.655945,-8.553704&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Monaleen (New Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=52.651317,-8.551751&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Mountcollins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.313746,-9.230157&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Murroe Boher',' ',' ','https://maps.google.co.uk/maps?daddr=52.651864,-8.399466&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Na Piarsaigh',' ',' ','https://maps.google.co.uk/maps?daddr=52.670358,-8.669887&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Newcastlewest',' ',' ','https://maps.google.co.uk/maps?daddr=52.452610,-9.064590&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Old Christians Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.646130,-8.624568&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Oola GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.529192,-8.263693&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Pallasgreen',' ',' ','https://maps.google.co.uk/maps?daddr=52.566425,-8.338602&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Pallaskenry',' ',' ','https://maps.google.co.uk/maps?daddr=52.641039,-8.864926&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Patrickswell',' ',' ','https://maps.google.co.uk/maps?daddr=52.598473,-8.713725&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Sean Finns GFC Rathkeale',' ',' ','https://maps.google.co.uk/maps?daddr=52.527632,-8.947946&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','South Liberties',' ',' ','https://maps.google.co.uk/maps?daddr=52.600826,-8.604484&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Kierans',' ',' ','https://maps.google.co.uk/maps?daddr=52.529309,-9.014035&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Marys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.527678,-8.947989&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Munchins College  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.683017,-8.615599&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Patricks',' ',' ','https://maps.google.co.uk/maps?daddr=52.668988,-8.596598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Pauls Mungret',' ',' ','https://maps.google.co.uk/maps?daddr=52.634762,-8.687814&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','St Senans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.607804,-9.102978&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Staker Wallace',' ',' ','https://maps.google.co.uk/maps?daddr=52.407269,-8.508675&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Templeglantine Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.401077,-9.167823&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','Tournafulla Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.368669,-9.146461&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('18','University of Limerick  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=52.672147,-8.563982&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_LONGFORD_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('19','Abbeylara GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.758778,-7.458483&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Ballymahon',' ',' ','https://maps.google.co.uk/maps?daddr=53.568351,-7.764394&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Ballymore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.771671,-7.536750&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Carrickedmond GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.601845,-7.712252&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Cashel GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.584512,-7.936410&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Clonguish',' ',' ','https://maps.google.co.uk/maps?daddr=53.761676,-7.833155&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Colmcilles GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.824874,-7.613997&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Cuchullainns Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.584875,-7.937220&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Dromard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.896449,-7.660432&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Emmet Og GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.762158,-7.733881&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Fr Manning Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.837215,-7.739643&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Grattan Og GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.693524,-7.818897&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Kenagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.623225,-7.819068&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Killoe Young Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.746002,-7.716501&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Legan Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.623556,-7.632666&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Longford Slashers',' ',' ','https://maps.google.co.uk/maps?daddr=53.717041,-7.801173&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Mostrim / Wolfe Tones GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.693130,-7.616229&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Moydow Harpers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.666428,-7.779822&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Pearse Park (Longford County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.739047,-7.804692&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Rathcline GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.675415,-7.972373&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Sean Connollys Ballinalee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.768671,-7.650840&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Shroid Slashers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.717473,-7.727476&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','St Brigids Killashee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.690398,-7.884761&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','St Columbas Mullinalaghta GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.822645,-7.527646&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','St Marys Granard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.772609,-7.488105&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','St Munis Forgney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.556977,-7.721972&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','St Patricks Ardagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.669250,-7.706673&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('19','Wolfe Tones Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.693092,-7.616068&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_LOUTH_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('20','Annaghminnon Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.965263,-6.601743&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Clan Na Gael GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.011451,-6.413473&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Cooley Kickhams GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.009591,-6.168877&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Cuchullain Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.082977,-6.255641&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Dowdallshill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.023799,-6.396285&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Dreadnots GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.778848,-6.246666&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Dundalk Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.999508,-6.400663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Dundalk IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.984055,-6.390513&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Dundalk Young Irelands GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.989537,-6.391028&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Gaelic Grounds',' ',' ','https://maps.google.co.uk/maps?daddr=53.723764,-6.359872&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Geraldines GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.964026,-6.403409&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Glen Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=53.738135,-6.423545&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Glyde Rangers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.917464,-6.546253&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Hunterstown Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.825925,-6.531866&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','John Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.879585,-6.520890&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Kilkerley Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.008752,-6.467342&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Knockbridge Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.967132,-6.477116&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Lannleire GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.830554,-6.405598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Louth  Centre of Excellence',' ',' ','https://maps.google.co.uk/maps?daddr=53.931622,-6.461613&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Mattock Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=53.774676,-6.480753&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Na Piarsaigh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.986597,-6.372768&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Naomh Fionnbarra GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.871780,-6.343993&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Naomh Malachi GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.045343,-6.563655&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Naomh Martin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.768976,-6.398474&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Naomh Moninne Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.023787,-6.396178&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Newtown Blues GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.726386,-6.325250&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Oliver Plunketts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.721942,-6.381716&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','OConnells',' ',' ','https://maps.google.co.uk/maps?daddr=53.894413,-6.387455&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','ORaghallaighs',' ',' ','https://maps.google.co.uk/maps?daddr=53.723809,-6.358670&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Pearse Og Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=Pearse+Og&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Roche Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.039616,-6.463158&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Sean McDermotts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.887408,-6.595305&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Sean OMahoneys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.007258,-6.376179&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Brides GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.967151,-6.477267&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Fechins',' ',' ','https://maps.google.co.uk/maps?daddr=53.759197,-6.277914&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Josephs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.932014,-6.408355&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Kevins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.822075,-6.469091&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Marys GFC Ardee',' ',' ','https://maps.google.co.uk/maps?daddr=53.841855,-6.542552&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Mochtas GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.950025,-6.549075&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Nicholas GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.713473,-6.371330&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.000864,-6.264535&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Stabannan Parnells GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.865549,-6.440188&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Westerns GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.910892,-6.608298&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Wolfe Tones GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.703833,-6.358809&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('20','Wolfe Tones Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=Donore+Rd%2C+Drogheda%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_MAYO_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('21','Achill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.933258,-9.915966&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Aghamore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.827553,-8.820412&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ardagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.123388,-9.252108&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ardnaree Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.114977,-9.144836&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Balla GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.803344,-9.140861&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballaghaderreen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.899098,-8.596330&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballina Stephenites GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.112075,-9.161868&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballinrobe GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.615646,-9.212251&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballintubber ',' ',' ','https://maps.google.co.uk/maps?daddr=53.757782,-9.306482&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballintubber GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.753697,-9.243364&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballycastle GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.287888,-9.359311&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballycroy GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.027751,-9.825264&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballyhaunis',' ',' ','https://maps.google.co.uk/maps?daddr=53.763845,-8.792464&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballyheane Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.800999,-9.319013&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Ballyvary Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.888635,-9.158607&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Belmullet GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.250327,-10.002505&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Belmullet Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.179240,-10.070354&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Bohola Moy Davitts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.991656,-9.114382&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Bonniconlon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.108207,-9.024925&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Breaffy GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.845894,-9.234459&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Burrishoole GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.888761,-9.540854&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Caiseal Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.958743,-8.804255&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Carramore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.686269,-9.084663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Castlebar Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.853520,-9.284123&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Castlebar Mitchels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.853583,-9.283072&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Charlestown Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.958712,-8.804201&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Claremorris',' ',' ','https://maps.google.co.uk/maps?daddr=53.722653,-8.994589&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Claremorris Mushroom Houses Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.738226,-9.000319&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Connacht  Centre of Excellence',' ',' ','https://maps.google.co.uk/maps?daddr=53.775982,-8.851579&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Crossmolina Deel Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.104905,-9.306225&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Davitts Ballindine GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.672250,-8.958482&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Eastern Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.724291,-8.875988&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Garrymore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.646864,-9.020258&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Glencorrib',' ',' ','https://maps.google.co.uk/maps?daddr=53.510727,-9.171374&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Hollymount GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.661024,-9.116957&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Inishturk',' ',' ','https://maps.google.co.uk/maps?daddr=Inishturk%2C+Mayo%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Islandeady GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.826901,-9.403530&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','James Stephens Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.112176,-9.161895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kilcommon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.235882,-9.654504&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kilfian GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.199975,-9.353485&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Killala GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.202642,-9.241101&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kilmaine GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.585251,-9.129317&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kilmeena GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.841191,-9.582428&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kilmovee Shamrocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.885726,-8.678813&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kiltane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.140782,-9.740195&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Kiltimagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.848685,-9.011396&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Knockmore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.025369,-9.175311&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Lacken GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.262135,-9.223419&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Lahardane McHales GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.985178,-9.324308&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Louisburgh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.758860,-9.808967&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','MacHale Park (Mayo County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.853938,-9.286505&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Mayo Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.762837,-9.118223&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Moygownagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.165399,-9.345825&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Moytura Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.624793,-9.212433&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Na n-Oileáin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.802178,-9.955362&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Parke Keelogues Crimlin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.910481,-9.196136&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Shrule Glencorrib GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.529110,-9.079825&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Swinford GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.934907,-8.951594&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','The Neale GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.537789,-9.272869&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Tooreen Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.828294,-8.760095&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Tourmakeady GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.662887,-9.353185&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('21','Westport',' ',' ','https://maps.google.co.uk/maps?daddr=53.807753,-9.521810&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_MEATH_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('22','Baconstown',' ',' ','https://maps.google.co.uk/maps?daddr=53.448050,-6.810531&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Ballinabrackey GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.396560,-7.108283&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Ballinlough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.733545,-7.052767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Ballivor GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.531090,-6.965713&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Bective GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.613526,-6.670386&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Bellewstown',' ',' ','https://maps.google.co.uk/maps?daddr=53.644708,-6.353091&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Blackhall Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=53.473431,-6.545277&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Boardsmill',' ',' ','https://maps.google.co.uk/maps?daddr=53.525768,-6.871648&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Carnaross GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.746909,-6.939486&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Castletown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.767765,-6.689665&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Clann na nGael',' ',' ','https://maps.google.co.uk/maps?daddr=53.626909,-6.909574&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Clonard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.450724,-7.022163&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Cortown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.684394,-6.851563&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Curraha GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.554230,-6.453878&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Donaghmore Ashbourne',' ',' ','https://maps.google.co.uk/maps?daddr=53.509687,-6.409847&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Drumbaragh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.727078,-6.937791&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Drumcondrath GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.842235,-6.656309&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Drumree',' ',' ','https://maps.google.co.uk/maps?daddr=53.502751,-6.586905&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Duleek Bellewstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.654228,-6.432796&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Dunderry',' ',' ','https://maps.google.co.uk/maps?daddr=53.606703,-6.772975&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Dunganny',' ',' ','https://maps.google.co.uk/maps?daddr=53.577525,-6.725843&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Dunsany GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.545306,-6.617546&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Dunshaughlin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.515339,-6.550781&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Gaeil Colmcille',' ',' ','https://maps.google.co.uk/maps?daddr=53.720390,-6.879737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilbride GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.453592,-6.396275&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilcloon',' ',' ','https://maps.google.co.uk/maps?daddr=53.437480,-6.601469&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kildalkey',' ',' ','https://maps.google.co.uk/maps?daddr=53.570622,-6.903083&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Killyon Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.484598,-6.972231&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilmainham St Kevins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.711231,-6.826555&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilmainhamwood GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.846198,-6.806202&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilmessan Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.565879,-6.647984&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kilskyre Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.688778,-6.991639&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Kiltale Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.523013,-6.660923&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Longwood',' ',' ','https://maps.google.co.uk/maps?daddr=53.455847,-6.935077&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Martry',' ',' ','https://maps.google.co.uk/maps?daddr=53.691891,-6.797447&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Meath Hill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.887250,-6.713687&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Moylagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.720101,-7.145469&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Moynalty GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.780908,-6.874470&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Moynalvey GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.468782,-6.664131&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Na Fianna Enfield',' ',' ','https://maps.google.co.uk/maps?daddr=53.412688,-6.822419&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Navan OMahonys',' ',' ','https://maps.google.co.uk/maps?daddr=53.650082,-6.691854&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Nobber GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.819029,-6.744554&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Oldcastle GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.745164,-7.174705&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Pairc Tailtean',' ',' ','https://maps.google.co.uk/maps?daddr=53.649258,-6.694676&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Rathkenny GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.741909,-6.617181&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Rathmoylan Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.488575,-6.798767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Ratoath',' ',' ','https://maps.google.co.uk/maps?daddr=53.510567,-6.477610&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Seneschelstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.683638,-6.587420&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Simonstown Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.671901,-6.682241&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Skyrne GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.583398,-6.548764&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Slane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.720679,-6.478339&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Brigids GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.761841,-7.234143&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Colmcilles Laytown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.695703,-6.280017&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Marys Donore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.693575,-6.445509&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Michaels Carlanstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.767454,-6.826190&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Patricks Stamullen',' ',' ','https://maps.google.co.uk/maps?daddr=53.634149,-6.257819&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Pauls Clonee GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.433188,-6.480517&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Peters Dunboyne',' ',' ','https://maps.google.co.uk/maps?daddr=53.410152,-6.473737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Ultans Bohermeen GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.671649,-6.814313&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','St Vincents Ardcath GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.602698,-6.383354&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Summerhill GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.483079,-6.738331&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Syddan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.795974,-6.636708&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Trim',' ',' ','https://maps.google.co.uk/maps?daddr=53.553382,-6.800730&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Walterstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.624688,-6.609628&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('22','Wolfe Tones Kilberry',' ',' ','https://maps.google.co.uk/maps?daddr=53.699298,-6.681544&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_MONAGHAN_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('23','Aghabog Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=54.170612,-7.046238&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Aughnamullen Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.061944,-6.814635&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Ballybay Pearse Brothers',' ',' ','https://maps.google.co.uk/maps?daddr=54.126097,-6.875362&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Blackhill Emeralds',' ',' ','https://maps.google.co.uk/maps?daddr=54.111132,-6.761516&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Carrickmacross Emmets GAC',' ',' ','https://maps.google.co.uk/maps?daddr=53.985127,-6.721294&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Castleblayney Faughs GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.112580,-6.731448&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Cloghan (Monaghan County Training Pitches)',' ',' ','https://maps.google.co.uk/maps?daddr=54.157798,-6.805054&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Clones St Tiernachs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.186814,-7.238493&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Clontibtret ONeills',' ',' ','https://maps.google.co.uk/maps?daddr=54.218407,-6.844428&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Corduff Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.010297,-6.821523&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Cremartin Shamrocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.160003,-6.814409&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Currin Sons of St Patrick GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.130536,-7.257360&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Donaghmoyne Fontenoys',' ',' ','https://maps.google.co.uk/maps?daddr=54.014603,-6.699418&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Doohamlet ONeills GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.130851,-6.819098&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Drumhowan Geraldines',' ',' ','https://maps.google.co.uk/maps?daddr=54.105046,-6.821190&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Eire Og GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.223004,-7.097849&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Emyvale',' ',' ','https://maps.google.co.uk/maps?daddr=54.331424,-6.948252&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Fergal OHanlons GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.246939,-6.990003&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Inniskeen Grattans',' ',' ','https://maps.google.co.uk/maps?daddr=53.992911,-6.604382&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Killanny Geraldines GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.963092,-6.646782&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Killeevan Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.164268,-7.142680&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Latton ORahillys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.073712,-6.948456&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Magheracloone Mitchells GAC',' ',' ','https://maps.google.co.uk/maps?daddr=53.944721,-6.770046&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Monaghan Harps',' ',' ','https://maps.google.co.uk/maps?daddr=54.248948,-6.961201&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Oram Sarsfields',' ',' ','https://maps.google.co.uk/maps?daddr=54.146064,-6.694901&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Rockcorry',' ',' ','https://maps.google.co.uk/maps?daddr=54.119464,-7.012791&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Scotstown',' ',' ','https://maps.google.co.uk/maps?daddr=54.268457,-7.054220&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Sean McDermotts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.215492,-7.041281&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','St Macartans College',' ',' ','https://maps.google.co.uk/maps?daddr=54.265391,-6.954683&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','St Tiernachs Park (Monaghan County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.185640,-7.233210&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Toome St Victors',' ',' ','https://maps.google.co.uk/maps?daddr=54.081378,-6.679248&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Truagh Gaels',' ',' ','https://maps.google.co.uk/maps?daddr=54.364408,-6.964935&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('23','Tyholland St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.267990,-6.918849&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_OFFALY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('24','Ballinagar GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.267391,-7.333074&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ballinamere Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.292156,-7.565122&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ballycommon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.283036,-7.365260&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ballycumber GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.332942,-7.706748&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ballyfore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.307269,-7.135481&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ballyskenagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.958922,-7.842468&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Belmont Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.242677,-7.938191&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Bracknagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.216904,-7.142165&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Brosna Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.332930,-7.706716&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Cappincur GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.274318,-7.449503&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Carrig Hurling Club Riverstown',' ',' ','https://maps.google.co.uk/maps?daddr=53.058484,-7.979228&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Clara',' ',' ','https://maps.google.co.uk/maps?daddr=53.338824,-7.632118&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Clonbullogue GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.262646,-7.084165&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Clonmore Harps GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.374682,-7.140421&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Coolderry',' ',' ','https://maps.google.co.uk/maps?daddr=53.013376,-7.844367&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Crinkle',' ',' ','https://maps.google.co.uk/maps?daddr=53.076316,-7.891628&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Daingean GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.301550,-7.292701&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Doon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.331994,-7.829969&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Drumcullen',' ',' ','https://maps.google.co.uk/maps?daddr=53.133184,-7.804134&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Durrow GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.315006,-7.511135&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Edenderry GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.350337,-7.053400&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Erin Rovers Pullough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.282494,-7.719741&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Ferbane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.269878,-7.821289&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Gracefield',' ',' ','https://maps.google.co.uk/maps?daddr=53.165475,-7.217320&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Kilclonfert GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.328385,-7.347445&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Kilcormac Killoughey',' ',' ','https://maps.google.co.uk/maps?daddr=53.176061,-7.732015&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Killavilla',' ',' ','https://maps.google.co.uk/maps?daddr=52.961366,-7.732664&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Killeigh',' ',' ','https://maps.google.co.uk/maps?daddr=53.215018,-7.447931&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Killurin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.215857,-7.552950&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Kinnitty',' ',' ','https://maps.google.co.uk/maps?daddr=53.100969,-7.709849&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Lusmagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.172955,-8.018528&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Mountbolus',' ',' ','https://maps.google.co.uk/maps?daddr=53.194882,-7.625520&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','OConnor Park (Offaly County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.280306,-7.488599&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Raheen',' ',' ','https://maps.google.co.uk/maps?daddr=53.238619,-7.328836&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Rhode GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.345440,-7.210797&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Seir Kieran Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.073925,-7.800003&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=53.252043,-7.541943&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Shannonbridge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.266131,-8.004184&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Shinrone Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.986309,-7.924554&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','St Brendans Park',' ',' ','https://maps.google.co.uk/maps?daddr=53.091698,-7.908504&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','St Brigids Croghan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.335992,-7.279623&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','St Rynaghs Cloghan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.221992,-7.884482&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','St Rynaghs Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.189052,-7.975302&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Tubber GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.373671,-7.658179&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Tullamore',' ',' ','https://maps.google.co.uk/maps?daddr=53.280403,-7.493153&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('24','Walsh Island GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.229352,-7.225174&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_ROSCOMMON_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('25','Athleague Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.574111,-8.267963&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Ballinameen  Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.907679,-8.301866&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Boyle',' ',' ','https://maps.google.co.uk/maps?daddr=53.974439,-8.296781&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Castlerea St Kevins',' ',' ','https://maps.google.co.uk/maps?daddr=53.769832,-8.487518&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Clann na nGael',' ',' ','https://maps.google.co.uk/maps?daddr=53.369801,-8.020894&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Creggs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.594386,-8.361953&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Dr. Hyde Park (Roscommon County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.624726,-8.181875&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Eire Og Loughglynn GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.847451,-8.576396&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Elphin GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.847505,-8.191643&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Four Roads Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.511097,-8.222558&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Fuerty GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.589853,-8.310530&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Kilbride GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.700244,-8.209105&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Kilglass Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.826185,-7.969900&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Kilmore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.874544,-8.052152&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Michael Glaveys Ballinlough GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.752048,-8.633151&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Oran',' ',' ','https://maps.google.co.uk/maps?daddr=53.659753,-8.276825&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Padraig Pearses Creagh',' ',' ','https://maps.google.co.uk/maps?daddr=53.336581,-8.153706&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Roscommon Gaels (Lismault)',' ',' ','https://maps.google.co.uk/maps?daddr=53.632590,-8.195490&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Shannon Gaels Croghan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.912283,-8.217725&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Aidans Ballyforan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.470264,-8.273091&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Barrys Termonbarry GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.742614,-7.925584&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Brigids Kiltoom GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.485358,-8.027433&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Croans Enfield GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.721676,-8.382279&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Dominics Knockcroghery',' ',' ','https://maps.google.co.uk/maps?daddr=53.571683,-8.076239&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Faithleachs Ballyleague GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.675593,-8.010149&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Josephs Kilteevan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.634976,-8.124655&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Michaels Cootehall GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.993151,-8.172686&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','St Ronans Ballyfarnon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.072053,-8.201074&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Strokestown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.767825,-8.103737&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Tremane Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.578181,-8.205339&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Tulsk GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.774118,-8.239564&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('25','Western Gaels Frenchpark GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.870407,-8.407502&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_SLIGO_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('26','Ballisodare',' ',' ','https://maps.google.co.uk/maps?daddr=54.213120,-8.521474&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Ballymote',' ',' ','https://maps.google.co.uk/maps?daddr=54.095371,-8.520439&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Bunninadden',' ',' ','https://maps.google.co.uk/maps?daddr=54.038721,-8.574936&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Calry/St Josephs',' ',' ','https://maps.google.co.uk/maps?daddr=54.269309,-8.417437&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Castleconnor',' ',' ','https://maps.google.co.uk/maps?daddr=54.165700,-9.078290&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Cloonacool',' ',' ','https://maps.google.co.uk/maps?daddr=54.115471,-8.758442&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Coolaney/Mullinabreena',' ',' ','https://maps.google.co.uk/maps?daddr=54.095323,-8.660327&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Coolera Strandhill',' ',' ','https://maps.google.co.uk/maps?daddr=54.244215,-8.542449&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Curry',' ',' ','https://maps.google.co.uk/maps?daddr=54.005221,-8.784009&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Drumcliffe Rosses Point',' ',' ','https://maps.google.co.uk/maps?daddr=54.339453,-8.520262&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Easkey',' ',' ','https://maps.google.co.uk/maps?daddr=54.289754,-8.979489&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Eastern Harps',' ',' ','https://maps.google.co.uk/maps?daddr=54.044366,-8.446909&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Enniscrone/Kilglass',' ',' ','https://maps.google.co.uk/maps?daddr=54.239162,-9.056007&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Geevagh',' ',' ','https://maps.google.co.uk/maps?daddr=54.101162,-8.240787&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Markievicz Park (Sligo County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.257529,-8.466414&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Naomh Eoin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.265331,-8.500983&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Owenmore Gaels Collooney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.177281,-8.490640&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Shamrock Gaels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.157113,-8.372097&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Sligo IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=54.278168,-8.463303&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Farnans Dromore West GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.242134,-8.817172&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Johns Carraroe GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.237920,-8.464204&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Marys Ballydoogan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.268037,-8.503665&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Michaels Ballintogher GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.199341,-8.363439&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Molaise Gaels Grange GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.395426,-8.518127&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','St Patricks Dromard GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.240284,-8.678899&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Tourlestrane',' ',' ','https://maps.google.co.uk/maps?daddr=54.038721,-8.834531&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Tubbercurry',' ',' ','https://maps.google.co.uk/maps?daddr=54.059060,-8.722672&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('26','Western Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.289704,-8.979532&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_TIPPERARY_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('27','Aherlow',' ',' ','https://maps.google.co.uk/maps?daddr=52.411297,-8.225133&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ardfinnan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.318081,-7.864408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Arravale Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.469263,-8.159296&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballina',' ',' ','https://maps.google.co.uk/maps?daddr=52.815320,-8.438895&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballinahinch',' ',' ','https://maps.google.co.uk/maps?daddr=52.790617,-8.314236&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballingarry',' ',' ','https://maps.google.co.uk/maps?daddr=52.586513,-7.544936&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballybacon Grange Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.273771,-7.871168&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballycahill',' ',' ','https://maps.google.co.uk/maps?daddr=52.687852,-7.910446&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballylooby Castlegrace',' ',' ','https://maps.google.co.uk/maps?daddr=52.315274,-8.002376&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballyneale Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.443968,-7.478224&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Ballyporren GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.272008,-8.098839&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Boherlahan Dualla',' ',' ','https://maps.google.co.uk/maps?daddr=52.569376,-7.895163&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Borris Ileigh',' ',' ','https://maps.google.co.uk/maps?daddr=52.750016,-7.953908&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Borrisokane',' ',' ','https://maps.google.co.uk/maps?daddr=52.995338,-8.124121&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Burgess',' ',' ','https://maps.google.co.uk/maps?daddr=52.857199,-8.261794&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Cahir',' ',' ','https://maps.google.co.uk/maps?daddr=52.372884,-7.924399&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Cappawhite',' ',' ','https://maps.google.co.uk/maps?daddr=52.578074,-8.165604&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Carrick Davins Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.348298,-7.419164&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Carrick Swans',' ',' ','https://maps.google.co.uk/maps?daddr=52.348337,-7.419205&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Cashel King Cormacs',' ',' ','https://maps.google.co.uk/maps?daddr=52.509829,-7.877165&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Clerihan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.409239,-7.747990&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Clonakenny',' ',' ','https://maps.google.co.uk/maps?daddr=52.885784,-7.818167&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Clonmel Commercials GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.353495,-7.712445&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Clonmel Og',' ',' ','https://maps.google.co.uk/maps?daddr=52.362734,-7.706523&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Clonoulty Rossmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.611941,-7.958055&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','County Camogie Grounds',' ',' ','https://maps.google.co.uk/maps?daddr=52.730796,-7.916980&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Drom Inch',' ',' ','https://maps.google.co.uk/maps?daddr=52.719275,-7.913289&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Eire Og Annacarty',' ',' ','https://maps.google.co.uk/maps?daddr=52.563210,-8.112695&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Eire Og Nenagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.867161,-8.214474&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Emly',' ',' ','https://maps.google.co.uk/maps?daddr=52.464346,-8.347289&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Fethard',' ',' ','https://maps.google.co.uk/maps?daddr=52.467697,-7.694952&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Fr Sheehys',' ',' ','https://maps.google.co.uk/maps?daddr=52.278569,-8.002499&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Galtee Rovers St Peacauns',' ',' ','https://maps.google.co.uk/maps?daddr=52.446947,-8.066261&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Glengar',' ',' ','https://maps.google.co.uk/maps?daddr=52.611641,-8.213297&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Golden Kilfeacle',' ',' ','https://maps.google.co.uk/maps?daddr=52.499869,-7.977211&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Gortnahoe Glengoole',' ',' ','https://maps.google.co.uk/maps?daddr=52.674118,-7.602684&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Grangemockler GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.444030,-7.478063&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Holycross Ballycahill',' ',' ','https://maps.google.co.uk/maps?daddr=52.649036,-7.876006&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Inane Rovers GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.967368,-7.786227&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','JK Brackens',' ',' ','https://maps.google.co.uk/maps?daddr=52.798208,-7.836792&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Kildangan',' ',' ','https://maps.google.co.uk/maps?daddr=52.929492,-8.243710&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Killea Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.823973,-7.871511&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Killenaule',' ',' ','https://maps.google.co.uk/maps?daddr=52.564120,-7.670034&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Kilruane McDonaghs',' ',' ','https://maps.google.co.uk/maps?daddr=52.941210,-8.041756&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Kilsheelan Kilcash',' ',' ','https://maps.google.co.uk/maps?daddr=52.363005,-7.582819&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Knock Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.844040,-7.765918&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Knockavilla Donaskeigh Kickhams',' ',' ','https://maps.google.co.uk/maps?daddr=52.559359,-8.050961&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Knockshegowna',' ',' ','https://maps.google.co.uk/maps?daddr=53.019237,-8.027396&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Lattin Cullen',' ',' ','https://maps.google.co.uk/maps?daddr=52.463978,-8.272469&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Lorrha Dorrha',' ',' ','https://maps.google.co.uk/maps?daddr=53.118438,-8.108913&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Loughmore Castleiney',' ',' ','https://maps.google.co.uk/maps?daddr=52.770120,-7.788202&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Marlfield Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.345958,-7.752904&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Moneygall',' ',' ','https://maps.google.co.uk/maps?daddr=52.882495,-7.948812&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Moycarkey Borris',' ',' ','https://maps.google.co.uk/maps?daddr=52.641573,-7.739852&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Moyle Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.399879,-7.692420&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Moyne Templetuohy',' ',' ','https://maps.google.co.uk/maps?daddr=52.784278,-7.721404&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Mullinahone CJ Kickhams',' ',' ','https://maps.google.co.uk/maps?daddr=52.514282,-7.501527&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Newcastle',' ',' ','https://maps.google.co.uk/maps?daddr=52.271286,-7.806907&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Newport',' ',' ','https://maps.google.co.uk/maps?daddr=52.708239,-8.394837&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Portroe',' ',' ','https://maps.google.co.uk/maps?daddr=52.885900,-8.347442&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Rockwell Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.443161,-7.880749&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Roscrea Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.967361,-7.786378&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Rosegreen',' ',' ','https://maps.google.co.uk/maps?daddr=52.467273,-7.830591&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Sean Treacys',' ',' ','https://maps.google.co.uk/maps?daddr=52.678802,-8.163244&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Semple Stadium (Tipperary County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.682318,-7.825114&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Shannon Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=53.014486,-8.217602&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Silvermines',' ',' ','https://maps.google.co.uk/maps?daddr=52.800919,-8.189315&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Skeheenarinky Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.271690,-8.096945&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Solohead',' ',' ','https://maps.google.co.uk/maps?daddr=52.511572,-8.212259&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','St Martins GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.345919,-7.752957&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','St Marys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.353527,-7.712558&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','St Patricks Drangan',' ',' ','https://maps.google.co.uk/maps?daddr=52.474762,-7.595994&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Templederry Kenyons',' ',' ','https://maps.google.co.uk/maps?daddr=52.775037,-8.074340&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Thurles Kickhams Rahealty Fennellys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.669558,-7.796136&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Thurles Sarsfields',' ',' ','https://maps.google.co.uk/maps?daddr=52.682659,-7.823156&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Toomevara',' ',' ','https://maps.google.co.uk/maps?daddr=52.849900,-8.041037&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('27','Upperchurch Drombane',' ',' ','https://maps.google.co.uk/maps?daddr=52.661343,-7.951838&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_TYRONE_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('28','Aghaloo ONeills GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.407361,-6.977767&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Aghyaran Saint Davogs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.677696,-7.703015&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Ardboe ODonovan Rossa GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.614517,-6.551671&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Augher St Macartans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.435913,-7.120278&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Beragh Red Knights GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.549952,-7.153097&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Brackaville Owen Roes GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.550394,-6.715286&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Brockagh Emmetts GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.561136,-6.600069&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Cappagh Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.601241,-7.254437&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Clann na nGael GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.825588,-7.226815&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Clonoe ORahillys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.533994,-6.676512&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Coalisland Na Fianna',' ',' ','https://maps.google.co.uk/maps?daddr=54.543057,-6.689740&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Cookstown Fr Rocks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.642420,-6.750625&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Derrylaughan Kevin Barrys GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.535940,-6.611200&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Derrytresk Fir An Chnoic GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.517762,-6.639583&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Dregish Pearse Óg',' ',' ','https://maps.google.co.uk/maps?daddr=54.667825,-7.460876&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Drumquin Wolfe Tones',' ',' ','https://maps.google.co.uk/maps?daddr=54.611951,-7.490916&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Dunnamagh  Pitch (Defunct  club)',' ',' ','https://maps.google.co.uk/maps?daddr=54.870162,-7.309095&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Eire Og Carrickmore Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.591007,-7.065629&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Eire Og GFC Clogher',' ',' ','https://maps.google.co.uk/maps?daddr=54.411938,-7.166315&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Eoghan Ruadh Hurling Club Dungannon',' ',' ','https://maps.google.co.uk/maps?daddr=54.508255,-6.787823&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Errigal Ciaran GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.524998,-7.070185&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Eskra Emmets GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.484537,-7.202321&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Healy Park (Tyrone County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=54.614387,-7.297636&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Naomh Colum Cille Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=54.538682,-6.649872&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Naomh Eoin Hurling Club Dromore',' ',' ','https://maps.google.co.uk/maps?daddr=54.515710,-7.451638&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Owen Roe ONeills GAC Leckpatrick',' ',' ','https://maps.google.co.uk/maps?daddr=54.842717,-7.358758&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Pearse Og Gaa Dregish',' ',' ','https://maps.google.co.uk/maps?daddr=54.667832,-7.460929&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Pearses GFC Fintona',' ',' ','https://maps.google.co.uk/maps?daddr=54.492732,-7.328653&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Pearses GFC Galbally',' ',' ','https://maps.google.co.uk/maps?daddr=54.543126,-6.904435&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Plunketts GAC Pomeroy',' ',' ','https://maps.google.co.uk/maps?daddr=54.593867,-6.936117&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Sarsfields GFC Drumragh',' ',' ','https://maps.google.co.uk/maps?daddr=54.570115,-7.360368&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Sigersons GFC Strabane',' ',' ','https://maps.google.co.uk/maps?daddr=54.817113,-7.458225&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Colmcilles GFC Carrickmore',' ',' ','https://maps.google.co.uk/maps?daddr=54.592409,-7.067106&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Dympnas GFC Dromore',' ',' ','https://maps.google.co.uk/maps?daddr=54.515757,-7.451477&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Endas GFC Omagh',' ',' ','https://maps.google.co.uk/maps?daddr=54.614284,-7.297561&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Eugenes GFC Newtownstewart',' ',' ','https://maps.google.co.uk/maps?daddr=54.718513,-7.370913&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Eugenes GFC Castlederg',' ',' ','https://maps.google.co.uk/maps?daddr=54.707609,-7.599020&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Josephs GFC Glenelly',' ',' ','https://maps.google.co.uk/maps?daddr=54.765846,-7.250247&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Malachys GAC Moortown',' ',' ','https://maps.google.co.uk/maps?daddr=54.632400,-6.513498&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Malachys GFC Edendork',' ',' ','https://maps.google.co.uk/maps?daddr=54.522576,-6.759177&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Marys GFC Killeeshil',' ',' ','https://maps.google.co.uk/maps?daddr=54.491804,-6.944025&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Marys GFC Killyclogher',' ',' ','https://maps.google.co.uk/maps?daddr=54.601151,-7.254742&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Marys GFC Killyman',' ',' ','https://maps.google.co.uk/maps?daddr=54.507467,-6.674237&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks GFC Donaghmore',' ',' ','https://maps.google.co.uk/maps?daddr=54.533875,-6.811598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks GFC Rock',' ',' ','https://maps.google.co.uk/maps?daddr=54.598988,-6.830914&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks Academy, Dungannon',' ',' ','https://maps.google.co.uk/maps?daddr=St+Patrick%5C%27s+Academy%2C+Killymeal+Road%2C+Dungannon%2C+United+Kingdom&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks GFC Eglish',' ',' ','https://maps.google.co.uk/maps?daddr=54.452177,-6.798177&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks GFC Gortin',' ',' ','https://maps.google.co.uk/maps?daddr=54.718002,-7.228435&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Patricks GFC Greencastle',' ',' ','https://maps.google.co.uk/maps?daddr=54.697067,-7.063892&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Teresas GFC Loughmacrory (New Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=54.627400,-7.106212&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','St Teresas GFC Loughmacrory (Old Pitch)',' ',' ','https://maps.google.co.uk/maps?daddr=54.625466,-7.110649&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Stewartstown Harps GAC',' ',' ','https://maps.google.co.uk/maps?daddr=54.566352,-6.689118&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Strabane Shamrocks Hurling-Na Seamroga',' ',' ','https://maps.google.co.uk/maps?daddr=54.817895,-7.457389&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Tattyreagh St Patricks GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.534438,-7.280996&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Thomas Clarkes GFC Dungannon',' ',' ','https://maps.google.co.uk/maps?daddr=54.508470,-6.786865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Tir na nOg GFC Moy',' ',' ','https://maps.google.co.uk/maps?daddr=54.443540,-6.697454&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Trillick St Macartans GFC',' ',' ','https://maps.google.co.uk/maps?daddr=54.452845,-7.490873&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Urney St Columbas',' ',' ','https://maps.google.co.uk/maps?daddr=54.793928,-7.531702&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('28','Wolfe Tones GAC Kildress',' ',' ','https://maps.google.co.uk/maps?daddr=54.639812,-6.924583&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_WATERFORD_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('29','Abbeyside Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.103138,-7.599106&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Affane GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.144037,-7.850118&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ardmore',' ',' ','https://maps.google.co.uk/maps?daddr=51.958449,-7.728657&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballinacourty GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.103013,-7.599530&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballinameela',' ',' ','https://maps.google.co.uk/maps?daddr=52.102739,-7.779136&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballinwillan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.128456,-7.869859&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballyduff Lower',' ',' ','https://maps.google.co.uk/maps?daddr=52.234985,-7.298913&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballyduff Upper',' ',' ','https://maps.google.co.uk/maps?daddr=52.141805,-8.052034&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballydurn Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.238641,-7.393616&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballygunner Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.226689,-7.065861&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ballysaggart',' ',' ','https://maps.google.co.uk/maps?daddr=52.184201,-7.991041&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Brickey Rangers',' ',' ','https://maps.google.co.uk/maps?daddr=52.088377,-7.691132&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Bunmahon',' ',' ','https://maps.google.co.uk/maps?daddr=52.152026,-7.368538&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Butlerstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.238369,-7.179539&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Cappoquin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.143991,-7.850053&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Clashmore Kinsellabeg',' ',' ','https://maps.google.co.uk/maps?daddr=52.005451,-7.819905&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Clonea Power Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.280696,-7.445211&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','De La Salle',' ',' ','https://maps.google.co.uk/maps?daddr=52.257277,-7.137358&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Dungarvan',' ',' ','https://maps.google.co.uk/maps?daddr=52.083970,-7.639768&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Dunhill',' ',' ','https://maps.google.co.uk/maps?daddr=52.175583,-7.249174&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Emmets GFC Colligan',' ',' ','https://maps.google.co.uk/maps?daddr=52.157381,-7.686557&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Erins Own',' ',' ','https://maps.google.co.uk/maps?daddr=52.251366,-7.112778&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Fenor',' ',' ','https://maps.google.co.uk/maps?daddr=52.161053,-7.223082&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Ferrybank',' ',' ','https://maps.google.co.uk/maps?daddr=52.266660,-7.097447&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Fews Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.220402,-7.444809&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Fourmilewater Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.269132,-7.723306&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Fraher Field',' ',' ','https://maps.google.co.uk/maps?daddr=52.096501,-7.623675&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Gaultier GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.159168,-7.016541&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Geraldines',' ',' ','https://maps.google.co.uk/maps?daddr=52.074818,-7.821246&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Glen Rovers Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.185070,-7.851427&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','John Mitchels GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.220366,-7.444632&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Kilgobinet',' ',' ','https://maps.google.co.uk/maps?daddr=52.156216,-7.653598&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Kill',' ',' ','https://maps.google.co.uk/maps?daddr=52.177542,-7.336413&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Kilmacthomas GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.202695,-7.424955&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Kilrosanty',' ',' ','https://maps.google.co.uk/maps?daddr=52.164186,-7.516569&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Knockanore Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=52.057960,-7.888978&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Lismore',' ',' ','https://maps.google.co.uk/maps?daddr=52.135039,-7.944762&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Modeligo',' ',' ','https://maps.google.co.uk/maps?daddr=52.144840,-7.746434&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Mount Melleray',' ',' ','https://maps.google.co.uk/maps?daddr=52.185129,-7.851437&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Mount Sion',' ',' ','https://maps.google.co.uk/maps?daddr=52.260541,-7.133963&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Newtown/Ballydurn',' ',' ','https://maps.google.co.uk/maps?daddr=52.238175,-7.393380&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Nire Gaa',' ',' ','https://maps.google.co.uk/maps?daddr=52.269159,-7.723389&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Old Parish',' ',' ','https://maps.google.co.uk/maps?daddr=52.001885,-7.632000&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Passage East Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.228729,-6.978169&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Portlaw',' ',' ','https://maps.google.co.uk/maps?daddr=52.301006,-7.309095&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Rathgormack',' ',' ','https://maps.google.co.uk/maps?daddr=52.310414,-7.480874&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Rinn Ó gCuanach',' ',' ','https://maps.google.co.uk/maps?daddr=52.043886,-7.570063&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Roanmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.257533,-7.137455&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Rockies Hurling Club Colligan',' ',' ','https://maps.google.co.uk/maps?daddr=52.157381,-7.686750&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Sliabh gCua St Marys ',' ',' ','https://maps.google.co.uk/maps?daddr=52.212807,-7.711651&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','St Marys Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.212856,-7.711587&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','St Mollerans',' ',' ','https://maps.google.co.uk/maps?daddr=52.343625,-7.426962&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','St Pauls',' ',' ','https://maps.google.co.uk/maps?daddr=52.249829,-7.130336&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','St Saviours',' ',' ','https://maps.google.co.uk/maps?daddr=52.241131,-7.138431&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Stradbally',' ',' ','https://maps.google.co.uk/maps?daddr=52.133369,-7.454588&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Tallow',' ',' ','https://maps.google.co.uk/maps?daddr=52.094398,-7.997977&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Tourin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.128529,-7.869902&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Tramore',' ',' ','https://maps.google.co.uk/maps?daddr=52.168335,-7.142143&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Walsh Park (Waterford County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.254334,-7.129435&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('29','Waterford Institute of Technology',' ',' ','https://maps.google.co.uk/maps?daddr=Carriganore+House%2C+Waterford+Institute+of+Technology+West+Campus%2C+Carriganore%2C+Ireland&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_WESTMEATH_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('30','Athlone Gaa',' ',' ','https://maps.google.co.uk/maps?daddr=53.428784,-7.927081&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Athlone IT  Pitch',' ',' ','https://maps.google.co.uk/maps?daddr=53.419245,-7.905082&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Ballinagore',' ',' ','https://maps.google.co.uk/maps?daddr=53.414542,-7.446408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Ballycomoyle Gaa',' ',' ','https://maps.google.co.uk/maps?daddr=53.723091,-7.297604&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Ballymore GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.499362,-7.662417&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Ballynacargy Gaa',' ',' ','https://maps.google.co.uk/maps?daddr=53.581312,-7.523360&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Brownstown Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.661720,-7.108173&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Bunbrosna',' ',' ','https://maps.google.co.uk/maps?daddr=53.586595,-7.445233&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Castledaly',' ',' ','https://maps.google.co.uk/maps?daddr=53.374772,-7.797385&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Castlepollard Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.680985,-7.309752&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Castletown Finea Coole Whitehall GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.753332,-7.333133&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Castletown Geoghegan Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.449011,-7.483599&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Caulry',' ',' ','https://maps.google.co.uk/maps?daddr=53.432223,-7.797042&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','CBS Mullingar',' ',' ','https://maps.google.co.uk/maps?daddr=53.530979,-7.342365&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Clonkill Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.571823,-7.273872&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Coralstown Kinnegad',' ',' ','https://maps.google.co.uk/maps?daddr=53.461118,-7.104592&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Crookedwood Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.601600,-7.284016&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Cullion Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.555568,-7.354703&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Cusack Park (Westmeath County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=53.527707,-7.338567&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Delvin',' ',' ','https://maps.google.co.uk/maps?daddr=53.603150,-7.104979&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Fr Daltons Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.498156,-7.660732&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Garrycastle',' ',' ','https://maps.google.co.uk/maps?daddr=53.424379,-7.896745&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Kilbeggan Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=53.369299,-7.507031&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Killucan GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.510331,-7.135170&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Lough Lene Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.649306,-7.225442&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Loughnavally',' ',' ','https://maps.google.co.uk/maps?daddr=53.484975,-7.540827&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Maryland',' ',' ','https://maps.google.co.uk/maps?daddr=53.470181,-7.764394&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Milltown',' ',' ','https://maps.google.co.uk/maps?daddr=53.538006,-7.545590&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Milltownpass',' ',' ','https://maps.google.co.uk/maps?daddr=53.440730,-7.248123&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Moate All Whites GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.391116,-7.699013&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Mullingar Shamrocks',' ',' ','https://maps.google.co.uk/maps?daddr=53.530711,-7.331432&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Raharney Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.519275,-7.121415&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Ringtown Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.643340,-7.300887&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Rochfortbridge',' ',' ','https://maps.google.co.uk/maps?daddr=53.423861,-7.284976&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Rosemount GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.427550,-7.642901&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Shandonagh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.510516,-7.402500&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Southern Gaels Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.428579,-7.927129&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Brigids Dalystown Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.416457,-7.303258&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Josephs Streamstown GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.431239,-7.558562&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Lomans Mullingar GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.534505,-7.314835&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Malachys GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.448999,-7.483578&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Marys Rochfortbridge GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.416483,-7.303097&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Oliver Plunketts Mullingar Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.534480,-7.341646&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St Pauls Clonmellon GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.666676,-7.021701&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','St. Fintas, Multyfarnham GAC',' ',' ','https://maps.google.co.uk/maps?daddr=53.623864,-7.385806&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Tang',' ',' ','https://maps.google.co.uk/maps?daddr=53.523000,-7.779082&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','The Downs',' ',' ','https://maps.google.co.uk/maps?daddr=53.507709,-7.241610&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Tubberclair',' ',' ','https://maps.google.co.uk/maps?daddr=53.482887,-7.862349&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Turin Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=53.579528,-7.203539&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('30','Tyrrellspass',' ',' ','https://maps.google.co.uk/maps?daddr=53.392690,-7.382813&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_WEXFORD_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('31','Askamore Kilrush',' ',' ','https://maps.google.co.uk/maps?daddr=52.642621,-6.555035&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Ballyfad Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.734944,-6.284794&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Ballygarrett Realt Na Mara',' ',' ','https://maps.google.co.uk/maps?daddr=52.583586,-6.232477&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Ballyhogue',' ',' ','https://maps.google.co.uk/maps?daddr=52.432629,-6.606525&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Bannow Ballymitty',' ',' ','https://maps.google.co.uk/maps?daddr=52.257756,-6.733536&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Buffers Alley',' ',' ','https://maps.google.co.uk/maps?daddr=52.563380,-6.328415&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Castletown Liam Mellows GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.709896,-6.205269&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Clonard',' ',' ','https://maps.google.co.uk/maps?daddr=52.338263,-6.477470&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Clonee',' ',' ','https://maps.google.co.uk/maps?daddr=52.622611,-6.480008&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Clongeen',' ',' ','https://maps.google.co.uk/maps?daddr=52.304405,-6.763630&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Cloughbawn',' ',' ','https://maps.google.co.uk/maps?daddr=52.466959,-6.731111&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Craanford Fr ORegans',' ',' ','https://maps.google.co.uk/maps?daddr=52.677768,-6.393796&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Crossabeg-Ballymurn',' ',' ','https://maps.google.co.uk/maps?daddr=52.422189,-6.464038&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Davidstown Courtnacuddy',' ',' ','https://maps.google.co.uk/maps?daddr=52.461743,-6.661727&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Duffry Rovers',' ',' ','https://maps.google.co.uk/maps?daddr=52.551724,-6.700287&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Faythe Harriers Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.349261,-6.485656&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Ferns St Aidens',' ',' ','https://maps.google.co.uk/maps?daddr=52.592556,-6.512007&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Geraldine OHanrahans',' ',' ','https://maps.google.co.uk/maps?daddr=52.404661,-6.933618&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Glynn Barntown',' ',' ','https://maps.google.co.uk/maps?daddr=52.374067,-6.569481&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Gorey Community School',' ',' ','https://maps.google.co.uk/maps?daddr=52.671978,-6.287946&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Gusserane ORahillys',' ',' ','https://maps.google.co.uk/maps?daddr=52.308026,-6.856112&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Half Way House Bunclody',' ',' ','https://maps.google.co.uk/maps?daddr=52.652723,-6.658916&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Horeswood',' ',' ','https://maps.google.co.uk/maps?daddr=52.311129,-6.958401&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Innovate Wexford Park (County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.332435,-6.475282&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Kilanerin-Ballyfad',' ',' ','https://maps.google.co.uk/maps?daddr=52.734979,-6.284759&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Kilmore',' ',' ','https://maps.google.co.uk/maps?daddr=52.208113,-6.545663&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Liam Mellows Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.709935,-6.205280&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Marshalstown Castledockrell',' ',' ','https://maps.google.co.uk/maps?daddr=52.565340,-6.571879&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Monageer-Boolavogue',' ',' ','https://maps.google.co.uk/maps?daddr=52.525504,-6.476998&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Naomh Eanna Gorey',' ',' ','https://maps.google.co.uk/maps?daddr=52.681137,-6.274277&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Oulart The Ballagh Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.506459,-6.387273&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Our Ladys Island',' ',' ','https://maps.google.co.uk/maps?daddr=52.204010,-6.374323&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Oylegate-Glenbrien',' ',' ','https://maps.google.co.uk/maps?daddr=52.417360,-6.504679&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Rapparees Starlights Hurling Club ',' ',' ','https://maps.google.co.uk/maps?daddr=52.503736,-6.576830&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Rathgarogue Cushinstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.363389,-6.844343&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Rathnure St Annes Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.497681,-6.765711&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Saint Endas  Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.681651,-6.291926&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Sarsfields GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.350873,-6.484637&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Shamrocks Enniscorthy',' ',' ','https://maps.google.co.uk/maps?daddr=52.506244,-6.555533&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Shelmaliers',' ',' ','https://maps.google.co.uk/maps?daddr=52.387276,-6.426970&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Abbans Adamstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.392213,-6.712443&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Annes Rathangan',' ',' ','https://maps.google.co.uk/maps?daddr=52.234265,-6.615894&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Brigids Blackwater',' ',' ','https://maps.google.co.uk/maps?daddr=52.442932,-6.353241&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Fintans',' ',' ','https://maps.google.co.uk/maps?daddr=52.243069,-6.458690&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St James Ramsgrange',' ',' ','https://maps.google.co.uk/maps?daddr=52.238287,-6.931955&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Johns Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.349412,-6.485614&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Josephs GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.334831,-6.469113&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Martins',' ',' ','https://maps.google.co.uk/maps?daddr=52.289655,-6.490839&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Marys GFC Maudlinstown',' ',' ','https://maps.google.co.uk/maps?daddr=52.351778,-6.485624&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Marys Rosslare',' ',' ','https://maps.google.co.uk/maps?daddr=52.243634,-6.400781&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Mogues Fethard on Sea',' ',' ','https://maps.google.co.uk/maps?daddr=52.183754,-6.837863&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Particks Ballyoughter',' ',' ','https://maps.google.co.uk/maps?daddr=52.613915,-6.426498&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St Patricks Park',' ',' ','https://maps.google.co.uk/maps?daddr=52.498067,-6.575307&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','St. Johns Volunteers',' ',' ','https://maps.google.co.uk/maps?daddr=52.349530,-6.485592&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Starlights GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.503706,-6.576873&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Taghmon Camross',' ',' ','https://maps.google.co.uk/maps?daddr=52.320927,-6.644497&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('31','Tara Rocks',' ',' ','https://maps.google.co.uk/maps?daddr=52.697870,-6.287709&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;

    private static final String INITIAL_WICKLOW_CLUB_INSERTS =
            "INSERT INTO " +
                    "Clubs " +
                    "(county_id_fk,club_name,colours,club_description,location,website)" +
                    "VALUES" +
                    "('32','An Tochar',' ',' ','https://maps.google.co.uk/maps?daddr=53.059348,-6.230171&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Annacurra',' ',' ','https://maps.google.co.uk/maps?daddr=52.841337,-6.354797&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Arklow Geraldines Ballymoney GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.820621,-6.177245&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Arklow Rock Parnells Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.790189,-6.175561&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Ashford GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.012253,-6.108828&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Aughrim',' ',' ','https://maps.google.co.uk/maps?daddr=52.851018,-6.338639&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Aughrim County Ground (Wicklow County Ground)',' ',' ','https://maps.google.co.uk/maps?daddr=52.852557,-6.336005&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Avoca',' ',' ','https://maps.google.co.uk/maps?daddr=52.854151,-6.216105&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Avondale',' ',' ','https://maps.google.co.uk/maps?daddr=52.922578,-6.241865&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Ballinacorr',' ',' ','https://maps.google.co.uk/maps?daddr=52.907181,-6.261735&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Ballymanus GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.871545,-6.410834&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Baltinglass',' ',' ','https://maps.google.co.uk/maps?daddr=52.931420,-6.683646&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Barndarrig',' ',' ','https://maps.google.co.uk/maps?daddr=52.927966,-6.156067&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Blessington',' ',' ','https://maps.google.co.uk/maps?daddr=53.180411,-6.539054&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Bray Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=53.207189,-6.127453&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Carnew Emmets',' ',' ','https://maps.google.co.uk/maps?daddr=52.714355,-6.492888&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Coolboy',' ',' ','https://maps.google.co.uk/maps?daddr=52.764917,-6.427892&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Coolkenno',' ',' ','https://maps.google.co.uk/maps?daddr=52.778587,-6.623232&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Donard The Glen (Donard/Glen)',' ',' ','https://maps.google.co.uk/maps?daddr=53.021715,-6.618426&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Dunlavin',' ',' ','https://maps.google.co.uk/maps?daddr=53.058207,-6.691586&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Eire Og Greystones',' ',' ','https://maps.google.co.uk/maps?daddr=53.132457,-6.067296&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Enniskerry',' ',' ','https://maps.google.co.uk/maps?daddr=53.193134,-6.182331&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Fergal Og',' ',' ','https://maps.google.co.uk/maps?daddr=53.186095,-6.125393&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Glenealy Hurling Club',' ',' ','https://maps.google.co.uk/maps?daddr=52.968899,-6.144887&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Hollywood GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.090667,-6.611216&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Kilbride GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.197562,-6.468973&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Kilcoole St Patricks',' ',' ','https://maps.google.co.uk/maps?daddr=53.094688,-6.070311&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Kilmacanogue GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.162874,-6.142559&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Kiltegan',' ',' ','https://maps.google.co.uk/maps?daddr=52.902949,-6.606807&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Knockananna GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.868055,-6.498080&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Lacken GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.176830,-6.511159&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Laragh GFC',' ',' ','https://maps.google.co.uk/maps?daddr=53.004984,-6.300241&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Newcastle',' ',' ','https://maps.google.co.uk/maps?daddr=53.070366,-6.057823&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Newtown',' ',' ','https://maps.google.co.uk/maps?daddr=53.080183,-6.115597&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Rathdrum/Ballinkill  (Centre of Excellence)',' ',' ','https://maps.google.co.uk/maps?daddr=52.949782,-6.210408&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Rathnew GFC',' ',' ','https://maps.google.co.uk/maps?daddr=52.989474,-6.081904&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Shillelagh',' ',' ','https://maps.google.co.uk/maps?daddr=52.755935,-6.531222&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','St Patricks',' ',' ','https://maps.google.co.uk/maps?daddr=52.973965,-6.026602&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Stratford/Grangecon ',' ',' ','https://maps.google.co.uk/maps?daddr=52.996771,-6.670718&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Tinahely',' ',' ','https://maps.google.co.uk/maps?daddr=52.798944,-6.458180&t=m&layer=1&doflg=ptk&om=1',' ')," +
                    "('32','Valleymount',' ',' ','https://maps.google.co.uk/maps?daddr=53.114185,-6.529988&t=m&layer=1&doflg=ptk&om=1',' ');"
            ;


    private final Context context;
    private MyDatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // we must pass the context from our class that we called from
    public DBManager(Context ctx) {
        this.context = ctx;
        DBHelper = new MyDatabaseHelper(context);
    }



    private static class MyDatabaseHelper extends SQLiteOpenHelper {

        public MyDatabaseHelper(Context context) {
            super(context, DATABASE, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_COUNTY_TABLE);
            db.execSQL(INITIAL_COUNTY_INSERTS);
            db.execSQL(CREATE_CLUB_TABLE);
            db.execSQL(INITIAL_ANTRIM_CLUB_INSERTS);
            db.execSQL(INITIAL_ARMAGH_CLUB_INSERTS);
            db.execSQL(INITIAL_CARLOW_CLUB_INSERTS);
            db.execSQL(INITIAL_CAVAN_CLUB_INSERTS);
            db.execSQL(INITIAL_CLARE_CLUB_INSERTS);
            db.execSQL(INITIAL_CORK_CLUB_INSERTS);
            db.execSQL(INITIAL_DERRY_CLUB_INSERTS);
            db.execSQL(INITIAL_DONEGAL_CLUB_INSERTS);
            db.execSQL(INITIAL_DOWN_CLUB_INSERTS);
            db.execSQL(INITIAL_DUBLIN_CLUB_INSERTS);
            db.execSQL(INITIAL_FERMANAGH_CLUB_INSERTS);
            db.execSQL(INITIAL_GALWAY_CLUB_INSERTS);
            db.execSQL(INITIAL_KERRY_CLUB_INSERTS);
            db.execSQL(INITIAL_KILDARE_CLUB_INSERTS);
            db.execSQL(INITIAL_KILKENNY_CLUB_INSERTS);
            db.execSQL(INITIAL_LAOIS_CLUB_INSERTS);
            db.execSQL(INITIAL_LEITRIM_CLUB_INSERTS);
            db.execSQL(INITIAL_LIMERICK_CLUB_INSERTS);
            db.execSQL(INITIAL_LONGFORD_CLUB_INSERTS);
            db.execSQL(INITIAL_LOUTH_CLUB_INSERTS);
            db.execSQL(INITIAL_MAYO_CLUB_INSERTS);
            db.execSQL(INITIAL_MEATH_CLUB_INSERTS);
            db.execSQL(INITIAL_MONAGHAN_CLUB_INSERTS);
            db.execSQL(INITIAL_OFFALY_CLUB_INSERTS);
            db.execSQL(INITIAL_ROSCOMMON_CLUB_INSERTS);
            db.execSQL(INITIAL_SLIGO_CLUB_INSERTS);
            db.execSQL(INITIAL_TIPPERARY_CLUB_INSERTS);
            db.execSQL(INITIAL_TYRONE_CLUB_INSERTS);
            db.execSQL(INITIAL_WATERFORD_CLUB_INSERTS);
            db.execSQL(INITIAL_WESTMEATH_CLUB_INSERTS);
            db.execSQL(INITIAL_WEXFORD_CLUB_INSERTS);
            db.execSQL(INITIAL_WICKLOW_CLUB_INSERTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUBS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTIES);

            onCreate(db);
        }
    }

    public DBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertClub(int county_id,String club_name, String colours, String club_description, String location, String website) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(FOREIGN_KEY_ID, county_id);
        initialValues.put(KEY_CLUB_NAME, club_name);
        initialValues.put(KEY_COLOURS, colours);
        initialValues.put(KEY_CLUB_DESCRIPTION, club_description);
        initialValues.put(KEY_CLUB_LOCATION, location);
        initialValues.put(KEY_CLUB_WEBSITE, website);

        return db.insert(TABLE_CLUBS, null, initialValues);
    }

    //for development use
    public long updateClub(int id,int county_id,String club_name, String colours, String club_description, String location, String website) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(FOREIGN_KEY_ID, county_id);
        initialValues.put(KEY_CLUB_NAME, club_name);
        initialValues.put(KEY_COLOURS, colours);
        initialValues.put(KEY_CLUB_DESCRIPTION, club_description);
        initialValues.put(KEY_CLUB_LOCATION, location);
        initialValues.put(KEY_CLUB_WEBSITE, website);

        return db.update(TABLE_CLUBS, initialValues, "_id = ?", new String[]{Integer.toString(id)});
    }



    public int insertCounty(String county_name) {

        int id = 0;
        Cursor cursor = null;

        try {

            cursor = db.query(TABLE_COUNTIES, // a. table
                    new String[]{KEY_COUNTY_ID}, // b. column names to return
                    KEY_COUNTY_NAME + " =? ", // c. selections "where clause"
                    new String[]{county_name}, // d. selections args "where values"
                    null, // e. group by
                    null, // f. having
                    null, // g. order by
                    null); // h. limit

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex(KEY_COUNTY_ID));
            }

            if (id == 0) {

                ContentValues initialValues = new ContentValues();
                initialValues.put(KEY_COUNTY_NAME, county_name);

                id = (int) db.insert(TABLE_COUNTIES, null, initialValues);
            }
        }finally {
            cursor.close();
        }



        return id;
    }

    public Cursor getClub(int countyQuery)
    {

        Cursor mCursor = db.rawQuery("SELECT * FROM Clubs JOIN Counties ON Clubs.county_id_fk = Counties._id WHERE county_id_fk = '"+ countyQuery +"' ORDER BY club_name;",null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor getClubNames(String clubQuery)
    {
        Log.d("MQ", clubQuery);
        String selectQuery = "SELECT * FROM Clubs JOIN Counties ON Clubs.county_id_fk = Counties._id WHERE Clubs.club_name = ?;";
        Cursor mCursor = db.rawQuery(selectQuery, new String[] {clubQuery});
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAll()
    {

        Cursor mCursor = db.query(TABLE_CLUBS, // a. table
                null, // b. column names to return
                null, // c. selections "where clause"
                null, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                KEY_CLUB_NAME, // g. order by
                null); // h. limit

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor getAllCounties()
    {

        Cursor countyCursor = db.query(TABLE_COUNTIES, // a. table
                null, // b. column names to return
                null, // c. selections "where clause"
                null, // d. selections args "where values"
                null, // e. group by
                null, // f. having
                KEY_COUNTY_NAME, // g. order by
                null); // h. limit

        if (countyCursor != null) {
            countyCursor.moveToFirst();
            Log.d("MQ", KEY_COUNTY_NAME);
        }
        return countyCursor;

    }

    public void delete(int id)
    {
        db.delete(TABLE_CLUBS,"_id = "+id,null);
    }



    public List<ClubsClass> searchLikeName(String likeName) {

        List<ClubsClass> clubNames = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Clubs WHERE club_name LIKE " + "'%"  + likeName + "%'" + ";", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ClubsClass clubsClass = new ClubsClass();
                clubsClass.setId(cursor.getInt(cursor.getColumnIndex(KEY_CLUB_ID)));
                clubsClass.setName(cursor.getString(cursor.getColumnIndex(KEY_CLUB_NAME)));
                clubsClass.setColors(cursor.getString(cursor.getColumnIndex(KEY_COLOURS)));
                clubsClass.setDecription(cursor.getString(cursor.getColumnIndex(KEY_CLUB_DESCRIPTION)));
                clubsClass.setLocaiton(cursor.getString(cursor.getColumnIndex(KEY_CLUB_LOCATION)));
                clubsClass.setWebsite(cursor.getString(cursor.getColumnIndex(KEY_CLUB_WEBSITE)));
                clubNames.add(clubsClass);

            } while (cursor.moveToNext());

            cursor.close();
        }
        return clubNames;

    }

    public ClubsClass searchClubID(int id) {

        if (id > 0) {

            List<ClubsClass> clubNames = new ArrayList<>();
            String query = "SELECT * FROM Clubs JOIN Counties ON Clubs.county_id_fk = Counties._id WHERE Clubs._id = " + id;
            Log.w("Query1", query);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ClubsClass clubsClass = new ClubsClass();
                    clubsClass.setId(cursor.getInt(0));
                    clubsClass.setName(cursor.getString(cursor.getColumnIndex(KEY_CLUB_NAME)));
                    clubsClass.setCountyName(cursor.getString(cursor.getColumnIndex(KEY_COUNTY_NAME)));
                    clubsClass.setColors(cursor.getString(cursor.getColumnIndex(KEY_COLOURS)));
                    clubsClass.setDecription(cursor.getString(cursor.getColumnIndex(KEY_CLUB_DESCRIPTION)));
                    clubsClass.setLocaiton(cursor.getString(cursor.getColumnIndex(KEY_CLUB_LOCATION)));
                    clubsClass.setWebsite(cursor.getString(cursor.getColumnIndex(KEY_CLUB_WEBSITE)));
                    clubNames.add(clubsClass);
                    Log.w("CLub", clubsClass.getName());

                } while (cursor.moveToNext());

                cursor.close();
            }
            if (clubNames.size() > 0) {
                return clubNames.get(0);
            } else {
                return null;
            }
        }
        return null;
    }
}

