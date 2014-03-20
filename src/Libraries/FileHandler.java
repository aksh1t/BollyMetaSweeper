package Libraries;

import java.io.File;
import java.util.*;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.*;

public class FileHandler {

	public Tag getMetaData(File file) {
		try {
			// Reading audiofile from method argument
			AudioFile af = AudioFileIO.read(file);

			// Getting tag from audiofile
			Tag tag = af.getTag();

			// Return proper field
			return tag;

		} catch (Exception e) {

			// Return null in case of exception
			return null;
		}
	}

	public boolean saveTag(File file) {
		try {
			
			String path = file.getAbsolutePath().replace(file.getName(), "");
			String newPath = path+cleanString(file.getName().replaceAll(".mp3", ""))+".mp3";
			
			File newFile = new File(newPath);
			
			try{
				file.renameTo(newFile);
			}catch(Exception e){
				//System.out.println("Exception in renaming file "+e);
			}
			
			// Creating AudioFile object from input File
			AudioFile af = AudioFileIO.read(newFile);

			// Getting tag from audiofile
			Tag tag = cleanTag(af.getTag());

			// Setting tag to audiofile
			af.setTag(tag);

			// Commit the audiofile
			af.commit();

			// Method successfully completed
			return true;

		} catch (Exception e) {

			// Method unsuccessful
			return false;

		}
	}

	private Tag cleanTag(Tag tag) {
		try {
			tag.setField(FieldKey.COMMENT, "");
		} catch (Exception e) {
			//System.out.println("Exception renaming comment "+e);
		} 
		for(FieldKey fk : FieldKey.values()){
			try {
				String newString = cleanString(tag.getFirst(fk));
				tag.setField(fk,newString);
			} catch (Exception e) {
				//System.out.println("Exception "+e);
			}
		}
		return tag;
	}

	private String cleanString(String tagString) {
		
		if(tagString.equals("")){
			return "";
		}
		
		tagString = tagString.trim().replaceAll("(?i)www.", "");

		if (tagString.toLowerCase().contains("songs.pk")) {
			tagString = tagString.replaceAll("(?i)songs.pk", "");
		}

		if (tagString.toLowerCase().contains("songspk.info")) {
			tagString = tagString.replaceAll("(?i)songspk.info", "");
		}

		if (tagString.toLowerCase().contains("djmaza.com")) {
			tagString = tagString.replaceAll("(?i)djmaza.com", "");
		}
		
		if (tagString.toLowerCase().contains("djmaza.info")) {
			tagString = tagString.replaceAll("(?i)djmaza.info", "");
		}
		
		if (tagString.toLowerCase().contains("mp3hungama.in")) {
			tagString = tagString.replaceAll("(?i)mp3hungama.in", "");
		}

		if (tagString.toLowerCase().contains("mp3khan.net")) {
			tagString = tagString.replaceAll("(?i)mp3khan.net", "");
		}
		
		if (tagString.toLowerCase().contains("mp3khan")) {
			tagString = tagString.replaceAll("(?i)mp3khan", "");
		}

		if (tagString.toLowerCase().contains("indiamp3.com")) {
			tagString = tagString.replaceAll("(?i)indiamp3.com", "");
		}

		if (tagString.toLowerCase().contains("funmaza.com")) {
			tagString = tagString.replaceAll("(?i)funmaza.com", "");
		}

		if (tagString.toLowerCase().contains("songspk.name")) {
			tagString = tagString.replaceAll("(?i)songspk.name", "");
		}

		tagString = tagString.replace(']',' ').replace('[', ' ');
		tagString = tagString.trim();

		if (tagString.endsWith("-")||tagString.endsWith("@")||tagString.endsWith("|")||tagString.endsWith(".")||tagString.endsWith(",")) {
			tagString = tagString.substring(0, tagString.length() - 2).trim();
		}

		return tagString;
	}

}
