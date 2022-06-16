package br.inatel.cdg.database.delete;

import br.inatel.cdg.database.brownie.Brownie;
import br.inatel.cdg.database.create.Create;
import br.inatel.cdg.database.interfaces.ManipulateData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Delete extends Brownie implements ManipulateData {

    int index = 0;
    boolean brownieExists = false;

    @Override
    public void selectItem(String brownieName){

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(database)) {

            Object obj = jsonParser.parse(reader);

            JSONArray brownieList = (JSONArray) obj;

            for (Object brownie : brownieList) {
                brownieExists = findItem((JSONObject) brownie, brownieName);
                if(brownieExists){
                    brownieList.remove(index);
                    break;
                }
                index++;
            }
            Create create = new Create();
            create.writeFile(brownieList);


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean findItem(JSONObject brownie, String brownieName) {
        return Objects.equals(brownie.get("Nome"), brownieName);
    }

    @Override
    public double getFinalPriceUnitary() {
        return 0;
    }

    @Override
    public double getFinalPriceTotal() {
        return 0;
    }

    public void getInfo(){
        System.out.println("Você tem certeza que deseja excluir o seguinte produto: " + "-" + "?");
    }
}
