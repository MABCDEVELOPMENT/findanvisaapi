
db.foot.find().forEach(function (doc) {
    
    var processo  = doc["processo"];
    var cnpj      = doc["cnpj"];

    //console.log(processo+" "+cnpj);
    
    var processoReturn = db.process.findOne({"cnpj":cnpj,"processo":processo});
    
    if (processoReturn!==null) {
        //console.log(processoReturn["_id"]);
        db.foot.update({"_id" :doc["_id"] },{$set : {"process":processoReturn}});
    } else {
        db.foot.update({"_id" :doc["_id"] },{$set : {"process":null}});
    }
 
});

db.saneanteProduct.find().forEach(function (doc) {
    
    var processo  = doc["processo"];
    var cnpj      = doc["cnpj"];

    //console.log(processo+" "+cnpj);
    
    var processoReturn = db.process.findOne({"cnpj":cnpj,"processo":processo});
    
    if (processoReturn!==null) {
        //console.log(processoReturn["_id"]);
        db.saneanteProduct.update({"_id" :doc["_id"] },{$set : {"process":processoReturn}});
    } else {
        db.saneanteProduct.update({"_id" :doc["_id"] },{$set : {"process":null}});
    }
 
});

db.saneanteNotification.find().forEach(function (doc) {
    
    var processo  = doc["processo"];
    var cnpj      = doc["cnpj"];
    var expedienteProcesso = doc["expedienteProcesso"];
    
    //console.log(processo+" "+expedienteProcesso+" "+cnpj);
    
    var processoReturn = db.process.findOne({"cnpj":cnpj,"processo":processo,"processDetail.processo.peticao.expediente":expedienteProcesso});
    if (processoReturn!==null) {
        //console.log(processoReturn["_id"]);
        db.saneanteNotification.update({"_id" :doc["_id"] },{$set : {"process":processoReturn}});
    } else {
        db.saneanteNotification.update({"_id" :doc["_id"] },{$set : {"process":null}});
    }
 
});

db.cosmeticNotification.find().forEach(function (doc) {
    
    var processo  = doc["processo"];
    var cnpj      = doc["cnpj"];

    //console.log(processo+" "+cnpj);
    
    var processoReturn = db.process.findOne({"cnpj":cnpj,"processo":processo});
    
    if (processoReturn!==null) {
        //console.log(processoReturn["_id"]);
        db.cosmeticNotification.update({"_id" :doc["_id"] },{$set : {"process":processoReturn}});
    } else {
        db.cosmeticNotification.update({"_id" :doc["_id"] },{$set : {"process":null}});
    }
 
});

db.cosmeticRegularized.find().forEach(function (doc) {
    
    var processo  = doc["processo"];
    var cnpj      = doc["cnpj"];

    //console.log(processo+" "+cnpj);
    
    var processoReturn = db.process.findOne({"cnpj":cnpj,"processo":processo});
    
    if (processoReturn!==null) {
        //console.log(processoReturn["_id"]);
        db.cosmeticRegularized.update({"_id" :doc["_id"] },{$set : {"process":processoReturn}});
    } else {
        db.cosmeticRegularized.update({"_id" :doc["_id"] },{$set : {"process":null}});
    }
 
});